package takenoko.entites;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import takenoko.ressources.Coordonnees;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JardinierGetDeplacementsPossibleStepDef {
    private ResponseEntity<ArrayList<Coordonnees>> response; // output

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Given("^on init une partie$")
    public void init() {
        restTemplate.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /JardinierGetDeplacementsPossible$")
    public void clientGETPandaGetDeplacementsPossible() {
        response = restTemplate.exchange("http://localhost:8080/0/JardinierGetDeplacementsPossible", HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<Coordonnees>>() {
                });
    }

    @Then("^le client reçoit un status code (\\d+)$")
    public void clientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();

        assertEquals(statusCode, currentStatusCode.value());
    }

    @And("^le client reçoit liste vide$")
    public void clientRecoitUneListeVide() {
        // le terrain etant vide aucuns déplacements possibles donc la liste est vide
        assertTrue(response.getBody().isEmpty());
    }

}
