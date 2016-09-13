Feature: Wordpress
  In order to build website
  As a developer
  I want to login to wordpress and build a website from available templates.

  Background:
    Given Wordpres is installed on device

  Scenario: login to wordpress with valid credentials
    When I am on the wordpress login page
    And I enter username and password
    And I click on submit button
    Then I should land on Welcome to wordpress page