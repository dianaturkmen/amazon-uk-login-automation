package com.qa.amazon.pages;

import com.qa.amazon.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class HomePage extends BasePage {

    private final By logo = By.cssSelector("#nav-logo-sprites, #nav-logo a");
    private final By searchBox = By.id("twotabsearchtextbox");
    private final By accountList = By.id("nav-link-accountList");
    private final By accountGreeting = By.id("nav-link-accountList-nav-line-1");
    private final By cookieBanner = By.id("sp-cc");
    private final By cookieAccept = By.id("sp-cc-accept");
    private final By cookieDecline = By.id("sp-cc-rejectall-link");
    private final By flyoutSignIn = By.cssSelector("#nav-flyout-ya-signin a, #nav-flyout-ya-signin");
    private final By flyoutNewCustomer = By.cssSelector("#nav-flyout-ya-newCust a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(ConfigReader.get("baseUrl"));
        waitForPageReady();
        skipIfCaptcha();
        return this;
    }

    public String title() { return driver.getTitle(); }

    public boolean isLogoDisplayed() { return isDisplayed(logo, 10); }

    public boolean isSearchBoxDisplayed() { return isDisplayed(searchBox, 10); }

    public boolean isCookieBannerDisplayed() { return isDisplayed(cookieBanner, 8); }

    public boolean isCookieBannerGone() {
        try {
            wait.until(d -> d.findElements(cookieBanner).isEmpty() || !d.findElement(cookieBanner).isDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptCookies() { click(cookieAccept); }

    public void declineCookies() { click(cookieDecline); }

    /** Dismisses the banner if present so it does not block other interactions. */
    public void dismissCookiesIfPresent() {
        if (isDisplayed(cookieAccept, 4)) {
            click(cookieAccept);
            isCookieBannerGone();
        }
    }

    public String accountGreetingText() { return visible(accountGreeting).getText().trim(); }

    public void hoverAccountList() {
        new Actions(driver).moveToElement(visible(accountList)).perform();
    }

    public boolean isFlyoutSignInDisplayed() { return isDisplayed(flyoutSignIn, 8); }

    public String flyoutNewCustomerText() { return visible(flyoutNewCustomer).getText().trim(); }

    public SignInPage clickSignIn() {
        click(accountList);
        waitForPageReady();
        skipIfCaptcha();
        return new SignInPage(driver);
    }
}
