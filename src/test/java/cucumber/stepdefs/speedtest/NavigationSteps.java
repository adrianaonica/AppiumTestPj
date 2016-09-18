package cucumber.stepdefs.speedtest;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import cucumber.stepdefs.BaseStepDefinition;

/**
 * Created by pritamkadam on 18/09/16.
 */
public class NavigationSteps extends BaseStepDefinition implements En{

    public NavigationSteps(){

        Given("^Speedtest is installed on device$", () -> {
            // Write code here that turns the phrase above into concrete actions

            throw new PendingException();
        });

        Given("^I am on Begin Test page$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        When("^I click on Begin Test button$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Then("^I should navigate to Home page$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
