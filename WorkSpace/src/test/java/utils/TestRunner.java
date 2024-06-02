package utils;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@io.cucumber.junit.CucumberOptions(
		plugin = {"pretty", "html:target/cucumber"},
		features = {"src/test/resources/feature/*.feature"},
		glue={"steps"},
		tags = "@NegitiveScenarios"
		)


public class TestRunner {

}
