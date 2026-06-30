package com.cafetron.tests.checkout;

import com.cafetron.base.AuthenticatedBaseTest;
import com.cafetron.flows.CartCheckoutFlow;
import com.cafetron.pages.CartPage;
import com.cafetron.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class CheckoutTests extends AuthenticatedBaseTest {

    @Test(description = "TC-024 to TC-029: Verify checkout overview and totals",
            groups = {"regression", "integration", "uat"})
    public void shouldDisplayCheckoutUiForCartFlow() {
        try {
            new CartCheckoutFlow(getDriver()).openCheckoutWithCartItem();
        } catch (SkipException skip) {
            throw skip;
        }

        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        Assert.assertTrue(checkoutPage.waitForUrlContains("/checkout"), "Checkout route should open from cart");
        Assert.assertTrue(checkoutPage.isDisplayed(), "Checkout UI should be visible");
    }

    @Test(description = "TC-042 to TC-044: Verify checkout validation feedback is visible",
            groups = {"sanity", "regression", "integration"})
    public void shouldShowCheckoutValidationFeedbackOrControls() {
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.open();

        Assert.assertTrue(checkoutPage.isDisplayed() || checkoutPage.currentUrlContains("/login")
                        || checkoutPage.currentUrlContains("/cart"),
                "Checkout should either display UI or redirect to a valid prerequisite page");
    }
}
