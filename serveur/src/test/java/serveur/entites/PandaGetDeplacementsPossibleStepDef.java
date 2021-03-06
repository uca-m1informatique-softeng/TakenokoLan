package serveur.entites;

import commun.ressources.Coordonnees;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import serveur.SpringRootTest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PandaGetDeplacementsPossibleStepDef extends SpringRootTest {
    private ResponseEntity<ArrayList<Coordonnees>> response; // output

    @Given("^une partie$")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /PandaGetDeplacementsPossible$")
    public void clientGETPandaGetDeplacementsPossible() {
        response = template.exchange("http://localhost:8080/0/PandaGetDeplacementsPossible", HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<Coordonnees>>() {
                });
    }

    @Then("^le client reçoit status code (\\d+)$")
    public void clientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();

        assertEquals(statusCode, currentStatusCode.value());
    }

    @And("^le client reçoit une liste vide$")
    public void clientRecoitUneListeVide() {
        // le terrain etant vide aucuns déplacements possibles donc la liste est vide
        assertTrue(response.getBody().isEmpty());
    }


}
