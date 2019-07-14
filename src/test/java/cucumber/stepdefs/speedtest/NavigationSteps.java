package cucumber.stepdefs.BitRise;

import com.mobile.os.android.ADB;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import cucumber.stepdefs.BaseStepDefinition;
import org.testng.Assert;

public class NavigationSteps extends BaseStepDefinition implements En{

    public NavigationSteps(){

        Given("^BitRise is installed on device", () -> {
            // Write code here that turns the phrase above into concrete actions
            ADB adb = new ADB(getCurrentDevice().getUdid());
            Assert.assertTrue(adb.getInstalledPackages().toString().contains("app-beta"));

        });

        Then("^User pass the onboarding screen by click on skip button", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Then("^Click “Get a job” button", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Then("^Click “Continue with Email” button", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Fill the “First Name”, “Last Name”, “Email” and “Password” fields", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Click “Sign up” button", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Check that job feed is open", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Click on “Applications” tab in tabbar", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Click on “Chats” tab in tabbar", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
       
        Then("^Click on “Profile” tab in tabbar", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
       
        Then("^Click “Settings” on profile screen", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Click “Log out”", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Confirm logout”", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        
        Then("^Check that user see screen with “Get a job” and “Hire staff”", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
