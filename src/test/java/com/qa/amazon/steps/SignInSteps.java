package com.qa.amazon.steps;

import com.qa.amazon.context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assume;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignInSteps {
    private final TestContext ctx;

    public SignInSteps(TestContext ctx) {
        this.ctx = ctx;
    }

    @Then("the sign-in page should be displayed")
    public void signInPageDisplayed() {
        assertTrue("Sign-in page not loaded. URL: " + ctx.signInPage().currentUrl(), ctx.signInPage().isLoaded());
    }

    @Then("the email input and Continue button should be displayed")
    public void emailAndContinue() {
        assertTrue("Email input missing", ctx.signInPage().isEmailInputDisplayed());
        assertTrue("Continue button missing", ctx.signInPage().isContinueDisplayed());
    }

    @When("the user clicks Continue without entering an email")
    public void continueEmpty() {
        ctx.signInPage().clickContinue();
    }

    @When("the user enters email {string} and clicks Continue")
    public void enterEmail(String email) {
        String resolved = email.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));
        ctx.signInPage().enterEmail(resolved);
        ctx.signInPage().clickContinue();
    }

    @Then("an error message containing {string} should be displayed")
    public void errorContains(String expected) {
        String err = ctx.signInPage().visibleErrorText();
        assertTrue("Expected error containing '" + expected + "' but was: '" + err + "'",
                err.toLowerCase().contains(expected.toLowerCase()));
    }

    @Then("an error or create-account prompt should be displayed")
    public void errorOrCreateAccount() {
        assertTrue("Neither an error nor a create-account prompt was shown",
                ctx.signInPage().isErrorOrCreateAccountShown());
    }

    @Then("the password step should not be displayed")
    public void noPasswordStep() {
        assertFalse("Password step unexpectedly shown", ctx.signInPage().isPasswordStepDisplayed());
    }

    // ---------- @credentials scenarios (need a real test account via env vars) ----------

    @When("the user signs in with the configured test account")
    public void signInWithAccount() {
        String email = System.getenv("AMAZON_EMAIL");
        String pwd = System.getenv("AMAZON_PASSWORD");
        Assume.assumeTrue("AMAZON_EMAIL / AMAZON_PASSWORD not set", email != null && pwd != null);
        ctx.signInPage().enterEmail(email);
        ctx.signInPage().clickContinue();
        ctx.signInPage().enterPassword(pwd);
        ctx.signInPage().clickSignInSubmit();
    }

    @When("the user signs in with the configured email and a wrong password")
    public void signInWrongPassword() {
        String email = System.getenv("AMAZON_EMAIL");
        Assume.assumeTrue("AMAZON_EMAIL not set", email != null);
        ctx.signInPage().enterEmail(email);
        ctx.signInPage().clickContinue();
        ctx.signInPage().enterPassword("Wrong-Pass-" + System.currentTimeMillis());
        ctx.signInPage().clickSignInSubmit();
    }
}
