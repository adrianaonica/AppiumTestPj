package cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by pritamkadam on 10/09/16.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber" },
        features = {"./src/test/resources/features/wordpress/"},
        glue = {"cucumber.stepdefs"}
)
public class CukesRunner {
}
