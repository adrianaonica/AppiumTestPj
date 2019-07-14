Feature: Speedtest navigation's for BitRise app



  Scenario: Navigate to Home Page
    Given BitRise is installed on device
    Then User pass the onboarding screen by click on skip button
    Then Click “Get a job” button
    Then Click “Continue with Email” button
    Then Fill the “First Name”, “Last Name”, “Email” and “Password” fields
    Then Click “Sign up” button
    Then Check that job feed is open
    Then Click on “Applications” tab in tabbar
    Then Click on “Chats” tab in tabbar
    Then Click on “Profile” tab in tabbar
    Then Click “Settings” on profile screen
    Then Click “Log out”
    Then Confirm logout
    Then Check that user see screen with “Get a job” and “Hire staff”
    