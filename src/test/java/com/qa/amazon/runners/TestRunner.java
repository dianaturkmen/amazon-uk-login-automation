package com.qa.amazon.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// Tags are overridable: mvn test -Dcucumber.filter.tags="@smoke"
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.qa.amazon.steps", "com.qa.amazon.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json",
                "junit:target/cucumber-reports/report.xml"
        },
        monochrome = true
)
public class TestRunner {
}
