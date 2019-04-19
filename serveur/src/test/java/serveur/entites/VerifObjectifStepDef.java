package serveur.entites;

import commun.moteur.Terrain;
import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.FeuilleJoueur;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import serveur.configuration.Takenoko;
import serveur.utilitaires.StatistiqueJoueur;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class VerifObjectifStepDef {
    private TestRestTemplate template = new TestRestTemplate();
    private ResponseEntity<CartesObjectifs> response; // output
    private ResponseEntity<ArrayList<CartesObjectifs>> response1; // output

    public FeuilleJoueur feuilleJoueur = new FeuilleJoueur("");

    public FeuilleJoueur getFeuilleJoueur() {
        return feuilleJoueur;
    }

    @Given("^une nouvelle partie$")
    public void initPartie() {
        template.getForEntity("http://localhost:8080/init", String.class);

    }

    @When("^le client appelle /VerifObjectifAccompli")
    public void verifObjectif() {

        response = template.exchange("http://localhost:8080/0/VerifObjectifAccompli", HttpMethod.GET, null,
                CartesObjectifs.class);
    }

    @Then("^le nombre de points du joueur est non null")
    public void nbPoint() {
        assertNotEquals(null,response.getBody());
    }

    @When("^le client appelle /FeuilleJoueurGetMainObjectif")
    public void verifMain() {
        response1 = template.exchange("http://localhost:8080/0/FeuilleJoueurGetMainObjectif", HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<CartesObjectifs>>() {
                });
    }

    @Then("^le nombre de carte objectif dans la main est different")
    public void nbCartes() {
        // 0 objectif dans sa main car il a pas piocher
        assertEquals(0,response1.getBody().size());
    }

}