@amazon @signin
Feature: Amazon UK homepage sign-in
  As a customer of amazon.co.uk
  I want to reach and use the sign-in form from the homepage
  So that I can access my account

  Background:
    Given the user opens the Amazon UK homepage

  @smoke @TC01 @US01
  Scenario: Homepage loads successfully
    Then the page title should contain "Amazon.co.uk"
    And the Amazon logo should be displayed
    And the search box should be displayed

  @TC02 @US02
  Scenario: Visitor accepts cookies
    Then the cookie consent banner should be displayed
    When the user accepts cookies
    Then the cookie consent banner should be closed

  @TC03 @US02
  Scenario: Visitor declines cookies
    Then the cookie consent banner should be displayed
    When the user declines cookies
    Then the cookie consent banner should be closed

  @smoke @TC04 @US01 @US03
  Scenario: Signed-out visitor sees the sign-in greeting
    Given the user has dismissed the cookie banner
    Then the account greeting should contain "sign in"

  @TC05 @US03
  Scenario: Account & Lists flyout shows sign-in options
    Given the user has dismissed the cookie banner
    When the user hovers over Account & Lists
    Then the Sign in button should be displayed in the flyout
    And the new customer link should contain "Start here"

  @smoke @TC06 @US03
  Scenario: Navigate from homepage to the sign-in page
    Given the user has dismissed the cookie banner
    When the user clicks Hello, sign in
    Then the sign-in page should be displayed
    And the email input and Continue button should be displayed

  @negative @TC07 @US04
  Scenario: Continue with an empty email shows a validation error
    Given the user has dismissed the cookie banner
    When the user clicks Hello, sign in
    And the user clicks Continue without entering an email
    Then an error message containing "email" should be displayed
    And the password step should not be displayed

  @negative @TC08 @US04
  Scenario Outline: Invalid or unregistered email cannot proceed to password step
    Given the user has dismissed the cookie banner
    When the user clicks Hello, sign in
    And the user enters email "<email>" and clicks Continue
    Then an error or create-account prompt should be displayed
    And the password step should not be displayed

    Examples:
      | email                                       |
      | qa.invalid.user.<timestamp>@example.invalid |
      | not-an-email@@                              |

  @credentials @TC09 @US05
  Scenario: Registered customer signs in with valid credentials
    Given the user has dismissed the cookie banner
    When the user clicks Hello, sign in
    And the user signs in with the configured test account
    Then the account greeting should contain "Hello"

  @credentials @negative @TC10 @US05
  Scenario: Registered customer enters a wrong password
    Given the user has dismissed the cookie banner
    When the user clicks Hello, sign in
    And the user signs in with the configured email and a wrong password
    Then an error message containing "password" should be displayed
