# Amazon.co.uk – Homepage Sign-In Automation

Java 11 + Selenium 4 + Cucumber 7 (JUnit4, PicoContainer) BDD test framework for the amazon.co.uk homepage sign-in flow.

## Contents
- `docs/USER_STORIES.md`: user stories and acceptance criteria (US-01 … US-05)
- `docs/TEST_CASES.md`: test cases (TC-01 … TC-11) with traceability to the user stories
- `src/test/resources/features/homepage_signin.feature`: Gherkin scenarios

## Structure
```
src/test/java/com/qa/amazon/
├── context/TestContext.java      # shared state (PicoContainer DI)
├── hooks/Hooks.java              # driver lifecycle, screenshots, page source on failure
├── pages/                        # Page Object Model
│   ├── BasePage.java             # waits, bot-check handling
│   ├── HomePage.java
│   └── SignInPage.java
├── runners/TestRunner.java
├── steps/                        # step definitions
└── utils/                        # ConfigReader, DriverFactory
src/test/resources/config.properties
```

## Requirements
- JDK 11+ and Maven 3.8+
- Chrome, Edge or Firefox (the driver is downloaded automatically by Selenium Manager)

## Run
```bash
mvn test                                         # all except @credentials
mvn test "-Dcucumber.filter.tags=@smoke"         # smoke only
mvn test "-Dbrowser=edge" "-Dheadless=true"      # override config
```

Scenarios that need a real account (`@credentials`, TC-09/TC-10) read the credentials from environment variables. They are never stored in the repo.
```bash
set AMAZON_EMAIL=...      &  set AMAZON_PASSWORD=...
mvn test "-Dcucumber.filter.tags=@credentials"
```

## Reports
- `target/cucumber-reports/report.html` (with screenshots)
- `target/cucumber-reports/report.json`, `report.xml`
- `target/page-sources/` holds the page HTML of failed steps

## Notes
- Tests run against the live production site, so markup and texts may change.
- When Amazon shows a CAPTCHA, the scenario is marked **skipped**. The framework does not try to bypass it.
- The cookie banner is served conditionally (by region or A/B test). If it does not appear, TC-02/TC-03 are reported as skipped rather than as passed.
