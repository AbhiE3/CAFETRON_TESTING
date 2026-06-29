package com.cafetron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

public class VendorOrdersPage extends BasePage {
    private final By page = By.id("vendor-orders-page");
    private final By grid = By.id("vendor-orders-grid");
    private final By emptyState = By.id("vendor-orders-empty-state");
    private final By errorNotice = By.id("vendor-orders-error-notice");
    private final By backToMenuLink = By.id("vendor-orders-back-to-menu-link");
    private final By manageMenuLink = By.id("vendor-orders-manage-menu-link");
    private final By profileLink = By.id("vendor-orders-profile-link");

    public VendorOrdersPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("/vendor/orders");
    }

    public boolean isDisplayed() {
        return isDisplayed(page);
    }

    public boolean hasQueueState() {
        return isDisplayed(grid) || isDisplayed(emptyState) || isDisplayed(errorNotice);
    }

    public boolean hasVendorNavigation() {
        return isDisplayed(manageMenuLink) && isDisplayed(profileLink);
    }

    public boolean waitForOrderVisible(String orderId, String itemName) {
        String normalizedOrderId = normalize("order #" + orderId);
        String normalizedItemName = normalize(itemName);
        try {
            wait.until(driver -> {
                String visibleText = normalize(visibleTextSnapshot());
                return visibleText.contains(normalizedOrderId)
                        && visibleText.contains(normalizedItemName);
            });
            return true;
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public String visibleTextSnapshot() {
        String text = getOptionalText(page).replaceAll("\\s+", " ").trim();
        return text.length() > 700 ? text.substring(0, 700) + "..." : text;
    }

    public boolean isBackToMenuVisible() {
        return isDisplayed(backToMenuLink);
    }

    public void clickBackToMenu() {
        click(backToMenuLink);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }
}
