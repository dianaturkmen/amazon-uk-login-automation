package com.qa.amazon.pages;

import com.qa.amazon.utils.ConfigReader;
import org.junit.Assume;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final By CAPTCHA = By.cssSelector("form[action*='validateCaptcha'], #captchacharacters");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicitWait")));
    }

    protected WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected void click(By by) {
        clickable(by).click();
    }

    protected void type(By by, String text) {
        WebElement el = visible(by);
        el.clear();
        el.sendKeys(text);
    }

    protected boolean isDisplayed(By by, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isPresentNow(By by) {
        List<WebElement> els = driver.findElements(by);
        return !els.isEmpty() && els.get(0).isDisplayed();
    }

    protected void waitForPageReady() {
        wait.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    /**
     * Amazon may serve a bot-check page to automated browsers. We do NOT try to bypass it:
     * the scenario is marked as skipped (assumption failure) with a clear reason.
     */
    public void skipIfCaptcha() {
        // AWS WAF interstitial completes on its own in a normal browser and reloads; just wait for it.
        By wafInterstitial = By.id("challenge-container");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> d.findElements(wafInterstitial).isEmpty());
            waitForPageReady();
        } catch (Exception e) {
            Assume.assumeTrue("Amazon bot-check interstitial did not clear - scenario skipped", false);
        }
        Assume.assumeFalse("Amazon served a CAPTCHA/bot-check page - scenario skipped (not bypassed by design)",
                isPresentNow(CAPTCHA));
    }
}
