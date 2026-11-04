package com.qa.amazon.context;

import com.qa.amazon.pages.HomePage;
import com.qa.amazon.pages.SignInPage;
import com.qa.amazon.utils.DriverFactory;

/** Shared per-scenario state, injected into step classes by PicoContainer. */
public class TestContext {
    private HomePage homePage;
    private SignInPage signInPage;

    public HomePage homePage() {
        if (homePage == null) homePage = new HomePage(DriverFactory.getDriver());
        return homePage;
    }

    public SignInPage signInPage() {
        if (signInPage == null) signInPage = new SignInPage(DriverFactory.getDriver());
        return signInPage;
    }

    public void setSignInPage(SignInPage page) { this.signInPage = page; }
}
