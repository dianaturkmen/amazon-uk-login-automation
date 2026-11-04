package com.qa.amazon.hooks;

import com.qa.amazon.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println(">>> START: " + scenario.getName() + " " + scenario.getSourceTagNames());
        DriverFactory.getDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            attachScreenshot(scenario, "failure");
            savePageSource(scenario);
        }
    }

    private void savePageSource(Scenario scenario) {
        try {
            java.nio.file.Path dir = java.nio.file.Paths.get("target", "page-sources");
            java.nio.file.Files.createDirectories(dir);
            String file = scenario.getName().replaceAll("[^A-Za-z0-9]+", "_") + ".html";
            java.nio.file.Files.write(dir.resolve(file),
                    DriverFactory.getDriver().getPageSource().getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception ignored) {
            // best effort
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        attachScreenshot(scenario, "final-state");
        System.out.println("<<< END:   " + scenario.getName() + " -> " + scenario.getStatus());
        DriverFactory.quit();
    }

    private void attachScreenshot(Scenario scenario, String name) {
        try {
            byte[] png = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(png, "image/png", name);
        } catch (Exception ignored) {
            // screenshot is best effort
        }
    }
}
