package com.qa.amazon.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/** Thread-safe WebDriver holder. Drivers are resolved by Selenium Manager (no manual chromedriver). */
public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() { }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(create());
        }
        return DRIVER.get();
    }

    private static WebDriver create() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBool("headless");
        WebDriver driver;
        switch (browser) {
            case "firefox": {
                FirefoxOptions o = new FirefoxOptions();
                if (headless) o.addArguments("-headless");
                driver = new FirefoxDriver(o);
                break;
            }
            case "edge": {
                EdgeOptions o = new EdgeOptions();
                if (headless) o.addArguments("--headless=new");
                o.addArguments("--lang=en-GB");
                driver = new EdgeDriver(o);
                break;
            }
            default: {
                ChromeOptions o = new ChromeOptions();
                if (headless) o.addArguments("--headless=new");
                o.addArguments("--lang=en-GB", "--disable-notifications");
                driver = new ChromeDriver(o);
            }
        }
        driver.manage().window().setSize(new Dimension(1400, 950));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeout")));
        return driver;
    }

    public static void quit() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            d.quit();
            DRIVER.remove();
        }
    }
}
