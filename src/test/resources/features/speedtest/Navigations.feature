Feature: Speedtest navigation's
  In order to deploy my app on google store / iOS market
  As a developer
  I want to verify all navigation's within my app.



  Scenario: Navigate to Home Page
    Given Speedtest is installed on device
    Given I am on Begin Test page
    When I click on Begin Test button
    Then I should navigate to Home page