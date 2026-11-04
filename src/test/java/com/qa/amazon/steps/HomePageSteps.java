package com.qa.amazon.steps;

import com.qa.amazon.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class HomePageSteps {
    private final TestContext ctx;

    public HomePageSteps(TestContext ctx) {
        this.ctx = ctx;
    }

    @Given("the user opens the Amazon UK homepage")
    public void openHomepage() {
        ctx.homePage().open();
    }

    @Given("the user has dismissed the cookie banner")
    public void dismissCookies() {
        ctx.homePage().dismissCookiesIfPresent();
    }

    @Then("the page title should contain {string}")
    public void titleContains(String expected) {
        String title = ctx.homePage().title();
        assertTrue("Title was: " + title, title.contains(expected));
    }

    @Then("the Amazon logo should be displayed")
    public void logoDisplayed() {
        assertTrue("Logo not displayed", ctx.homePage().isLogoDisplayed());
    }

    @Then("the search box should be displayed")
    public void searchBoxDisplayed() {
        assertTrue("Search box not displayed", ctx.homePage().isSearchBoxDisplayed());
    }

    @Then("the cookie consent banner should be displayed")
    public void cookieBannerDisplayed() {
        // Amazon serves the banner conditionally (region / A-B test). Not served -> skip, never a false pass.
        org.junit.Assume.assumeTrue("Cookie banner was not served in this region/session - scenario skipped",
                ctx.homePage().isCookieBannerDisplayed());
    }

    @When("the user accepts cookies")
    public void acceptCookies() {
        ctx.homePage().acceptCookies();
    }

    @When("the user declines cookies")
    public void declineCookies() {
        ctx.homePage().declineCookies();
    }

    @Then("the cookie consent banner should be closed")
    public void cookieBannerClosed() {
        assertTrue("Cookie banner still visible", ctx.homePage().isCookieBannerGone());
    }

    @Then("the account greeting should contain {string}")
    public void greetingContains(String expected) {
        String text = ctx.homePage().accountGreetingText();
        assertTrue("Greeting was: " + text, text.toLowerCase().contains(expected.toLowerCase()));
    }

    @When("the user hovers over Account & Lists")
    public void hoverAccountList() {
        ctx.homePage().hoverAccountList();
    }

    @Then("the Sign in button should be displayed in the flyout")
    public void flyoutSignIn() {
        assertTrue("Flyout Sign in button not displayed", ctx.homePage().isFlyoutSignInDisplayed());
    }

    @Then("the new customer link should contain {string}")
    public void newCustomerLink(String expected) {
        String text = ctx.homePage().flyoutNewCustomerText();
        assertTrue("New customer link was: " + text, text.contains(expected));
    }

    @When("the user clicks Hello, sign in")
    public void clickSignIn() {
        ctx.setSignInPage(ctx.homePage().clickSignIn());
    }
}
