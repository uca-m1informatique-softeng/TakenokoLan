package serveur.entites;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/ressources/features/entites")
public class LancementCucumberEntitesTest {

    // will run all features found on the classpath in the same package as this class
}