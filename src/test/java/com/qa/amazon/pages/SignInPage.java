package com.qa.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SignInPage extends BasePage {

    // Amazon has used several variants of the sign-in form; selectors cover them.
    private final By emailInput = By.cssSelector("#ap_email, #ap_email_login, input[name='email']");
    private final By continueBtn = By.cssSelector("#continue input, input#continue, #continue, input[type='submit']");
    private final By passwordInput = By.cssSelector("#ap_password, input[name='password']");
    private final By signInSubmit = By.id("signInSubmit");
    private final By errorMessages = By.cssSelector(
            "#empty-claim-alert .a-alert-content, #auth-email-missing-alert .a-alert-content, "
          + "#invalid-email-alert .a-alert-content, #auth-error-message-box .a-alert-content, "
          + "#auth-email-invalid-claim-alert .a-alert-content, .a-alert-inline-error .a-alert-content, "
          + "#auth-password-missing-alert .a-alert-content");
    private final By createAccountPrompt = By.cssSelector(
            "#ap_register_form, #intention-submit-button, #createAccountSubmit, form[name='register']");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(emailInput, 15);
    }

    public String currentUrl() { return driver.getCurrentUrl(); }

    public boolean isEmailInputDisplayed() { return isDisplayed(emailInput, 10); }

    public boolean isContinueDisplayed() { return isDisplayed(continueBtn, 10); }

    public void enterEmail(String email) { type(emailInput, email); }

    public void clickContinue() {
        // Make sure the form is interactive (client-side validation bound) before submitting.
        waitForPageReady();
        clickable(emailInput).click();
        click(continueBtn);
        waitForPageReady();
        skipIfCaptcha();
    }

    public void enterPassword(String password) { type(passwordInput, password); }

    public void clickSignInSubmit() {
        click(signInSubmit);
        waitForPageReady();
        skipIfCaptcha();
    }

    /** Returns the first visible validation/error text on the page, or empty string. */
    public String visibleErrorText() {
        try {
            wait.until(d -> firstVisibleError() != null);
        } catch (Exception ignored) {
            return "";
        }
        WebElement e = firstVisibleError();
        return e == null ? "" : e.getText().trim();
    }

    private WebElement firstVisibleError() {
        List<WebElement> els = driver.findElements(errorMessages);
        for (WebElement e : els) {
            if (e.isDisplayed() && !e.getText().trim().isEmpty()) return e;
        }
        return null;
    }

    public boolean isPasswordStepDisplayed() { return isDisplayed(passwordInput, 5); }

    public boolean isErrorOrCreateAccountShown() {
        return !visibleErrorText().isEmpty() || isDisplayed(createAccountPrompt, 3);
    }
}
