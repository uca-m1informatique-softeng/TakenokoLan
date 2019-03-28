package serveur.entites;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import commun.ressources.Coordonnees;

import static org.junit.Assert.assertEquals;

public class PandaGetCoordonneesStepDef {

    private TestRestTemplate template = new TestRestTemplate();
    private ResponseEntity<Coordonnees> response; // output

    @Given("^besoin d'une partie$")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /PandaGetCoordonnees")
    public void pandaGetCoordonnees() {
        response = template.exchange("http://localhost:8080/0/PandaGetCoordonnees", HttpMethod.GET, null,
                Coordonnees.class);
    }

    @Then("^le client reçoit le status code (\\d+)$")
    public void clientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();

        assertEquals(statusCode, currentStatusCode.value());
    }

    @And("^le client reçoit les coordonnees du panda$")
    public void clientRecoitCoordonneesPanda() {
        //par defaut le panda est a la coordonnee 0,0,0
        assertEquals(response.getBody(), new Coordonnees(0, 0, 0));
    }
}
