package takenoko.entites;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import takenoko.ressources.Coordonnees;

import static org.junit.Assert.assertEquals;

public class JardinierGetCoordonneesStepDef {

    private TestRestTemplate template = new TestRestTemplate();
    private ResponseEntity<Coordonnees> response; // output

    @Given("^one partie$")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /JardinierGetCoordonnees")
    public void jardinierGetCoordonnees() {
        response = template.exchange("http://localhost:8080/0/JardinierGetCoordonnees", HttpMethod.GET, null,
                Coordonnees.class);
    }

    @Then("^client reçoit le status code (\\d+)$")
    public void clientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();

        assertEquals(statusCode, currentStatusCode.value());
    }

    @And("^le client reçoit les coordonnees du jardinier")
    public void clientRecoitCoordonneesPanda() {
        //par defaut le panda est a la coordonnee 0,0,0
        assertEquals(response.getBody(),new Coordonnees(0,0,0));
    }
}
