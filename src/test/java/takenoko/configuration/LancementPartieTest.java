package takenoko.configuration;

import org.junit.runner.RunWith;
        import cucumber.api.junit.Cucumber;
        import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/ressources/features")
public class LancementPartieTest {

    // will run all features found on the classpath in the same package as this class
}