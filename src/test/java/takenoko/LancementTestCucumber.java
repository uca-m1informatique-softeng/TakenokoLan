package takenoko;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/ressources/features")
public class LancementTestCucumber {

    // will run all features found on the classpath in the same package as this class
}