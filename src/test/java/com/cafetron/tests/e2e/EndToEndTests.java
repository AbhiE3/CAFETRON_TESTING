package com.cafetron.tests.e2e;

import com.cafetron.base.BaseTest;
import com.cafetron.data.Role;
import com.cafetron.data.TestDataFactory;
import com.cafetron.flows.AuthFlow;
import com.cafetron.flows.CartCheckoutFlow;
import com.cafetron.pages.CartPage;
import com.cafetron.pages.CheckoutPage;
import com.cafetron.pages.VendorMenuManagePage;
import com.cafetron.pages.VendorOrdersPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class EndToEndTests extends BaseTest {

    @Test(description = "TC-075: Employee places vendor-owned order and vendor can see it",
            groups = {"e2e", "integration", "uat"})
    public void shouldPlaceEmployeeOrderVisibleToVendor() {
        AuthFlow authFlow = new AuthFlow(getDriver());
        String itemName = TestDataFactory.uniqueName("E2E Vendor Item");

        authFlow.loginAs(Role.VENDOR);
        VendorMenuManagePage managePage = new VendorMenuManagePage(getDriver());
        managePage.open();
        managePage.createItem(itemName, "25", "5", "Snack");
        Assert.assertTrue(managePage.waitForItemCountAtLeast(itemName, 1),
                "Vendor setup item should be visible before employee orders it. Visible menu items: "
                        + managePage.menuItemNamesSnapshot());

        authFlow.logoutThroughProfile();
        authFlow.loginAs(Role.EMPLOYEE);
        CartPage cartPage = new CartPage(getDriver());
        cartPage.open();
        cartPage.clearIfAvailable();

        try {
            Assert.assertTrue(new CartCheckoutFlow(getDriver()).addMenuItemNamedToCart(itemName),
                    "Employee should be able to add the vendor-owned item to cart: " + itemName);
        } catch (SkipException skip) {
            throw skip;
        }

        cartPage.open();
        Assert.assertTrue(cartPage.isCheckoutAvailable(), "Employee cart should be ready for checkout");
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.waitForUrlContains("/checkout");
        Assert.assertTrue(checkoutPage.isDisplayed(), "Checkout should open for the employee order flow");
        checkoutPage.enterPickupLocation("QA E2E Pickup");
        Assert.assertTrue(checkoutPage.selectFirstPickupWindow(),
                "A pickup window should be available for the order.");
        Assert.assertTrue(checkoutPage.advanceToPlaceOrderStep(),
                "Checkout should advance to the final place-order step.");
        Assert.assertTrue(checkoutPage.placeOrderAndWaitForConfirmation(),
                "Order should be placed and route to order confirmation. Current state: "
                        + checkoutPage.visibleTextSnapshot());
        String orderId = checkoutPage.confirmedOrderId();
        Assert.assertFalse(orderId.isBlank(), "Order confirmation URL should include an order id.");

        authFlow.logoutThroughProfile();
        authFlow.loginAs(Role.VENDOR);
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(getDriver());
        vendorOrdersPage.open();
        Assert.assertTrue(vendorOrdersPage.waitForOrderVisible(orderId, itemName),
                "Vendor should see the employee order " + orderId + " for " + itemName
                        + ". Visible vendor orders: " + vendorOrdersPage.visibleTextSnapshot());
    }
}
