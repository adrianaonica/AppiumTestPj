package cucumber.stepdefs;

import cucumber.api.java8.En;

/**
 * Created by pritamkadam on 09/09/16.
 */
public class WordpressSteps implements En{


    public WordpressSteps(){

        Given("^The wordpress application file present in apps folder$", () -> {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("Lambda ran successfully");

        });


    }

}
