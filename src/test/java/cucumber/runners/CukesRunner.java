package cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber" },
        features = {"./src/test/resources/features/speedtest/"},
        glue = {"cucumber.stepdefs"}
)
public class CukesRunner {
}
