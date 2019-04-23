package serveur.entites;


import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.Parcelle;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ActionsJoueurStepDef {

    private Parcelle p = new Parcelle(new Coordonnees(1, 0, -1));
    private TestRestTemplate template = new TestRestTemplate();

    private ResponseEntity<String> response; // output
    private ResponseEntity<ArrayList<Parcelle>> response4; // output
    private ResponseEntity<ArrayList<Coordonnees>> response5; // output

    @Given("^la partie")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @And("^des parcelles")
    public void initParcelle() {
        p.setCouleur(Parcelle.Couleur.VERTE);
    }


    @When("^le client appelle /GetListeZonesPosables")
    public void zonePosable() {
        response5 = template.exchange("http://localhost:8080/0/GetListeZonesPosables", HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<Coordonnees>>() {
                });
    }

    @Then("^le client recoit statut code (\\d+)$")
    public void leClientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response5.getStatusCode();
        assertEquals(statusCode, currentStatusCode.value());
    }

    @When("^le client appelle/PoserParcelle")
    public void poserParcelle() {
        initParcelle();
        HttpEntity<Parcelle> request = new HttpEntity<>(p);
        response = template.exchange("http://localhost:8080/0/PoserParcelle", HttpMethod.POST, request,
                String.class);

    }

    @Then("^client recoit statut code (\\d+) and done$")
    public void clientRecoitStatusCodeAndDone(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();
        assertEquals(statusCode, currentStatusCode.value());
        assertEquals("done", response.getBody());
    }

    @When("^client appelle /GetZoneJouee")
    public void zoneJouee() {
        response4 = template.exchange("http://localhost:8080/0/GetZoneJouee", HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<Parcelle>>() {
                });
    }

    @Then("^client recoit statut code (\\d+)$")
    public void clientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response4.getStatusCode();
        assertEquals(statusCode, currentStatusCode.value());
    }

    @Then("^une parcelle est posée sur le terrain")
    public void parcellePosee() {
        // 2 avec la source
        Assert.assertEquals(2, response4.getBody().size());
    }

    @When("^le client appelle /DeplacerJardinier")
    public void deplacementJardinier() {
        poserParcelle(); // a cause du Background on doit reposer la parcelle
        HttpEntity<Coordonnees> request = new HttpEntity<>(p.getCoord());
        response = template.exchange("http://localhost:8080/0/DeplacerJardinier", HttpMethod.POST, request,
                String.class);
    }

    @Then("^si c'est possible le jardinier est déplacé sur le terrain")
    public void deplacementJardinierEffectue() {
        ResponseEntity<Coordonnees> temp = template.exchange(
                "http://localhost:8080/0/JardinierGetCoordonnees",
                HttpMethod.GET,
                null,
                Coordonnees.class);
        Assert.assertEquals(p.getCoord(), temp.getBody());
        assertEquals("done", response.getBody());
    }


    @When("^le client appelle /DeplacerPanda")
    public void deplacementPanda() {
        poserParcelle();// a cause du Background on doit reposer la parcelle
        HttpEntity<Coordonnees> request = new HttpEntity<>(p.getCoord());
        response = template.exchange("http://localhost:8080/0/DeplacerPanda", HttpMethod.POST, request,
                String.class);
    }

    @Then("^si c'est possible le panda est déplacé sur le terrain")
    public void deplacementPandaEffectue() {
        ResponseEntity<Coordonnees> temp = template.exchange(
                "http://localhost:8080/0/PandaGetCoordonnees",
                HttpMethod.GET,
                null,
                Coordonnees.class);
        Assert.assertEquals(p.getCoord(), temp.getBody());
        assertEquals("done", response.getBody());
    }


    @When("^le client appelle /PiocheUnObjectif")
    public void piocheObjectif() {
        HttpEntity<Integer> request = new HttpEntity<>(2);//2 pour objectif panda
        response = template.exchange("http://localhost:8080/0/PiocherUnObjectif", HttpMethod.POST, request,
                String.class);
        assertEquals("done", response.getBody());
    }

    @Then("^return au moins une carte")
    public void returnCard() {
        ResponseEntity<ArrayList<CartesObjectifs>> temp = template.exchange("http://localhost:8080/0/FeuilleJoueurGetMainObjectif", HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<CartesObjectifs>>() {
                });
        assertEquals(1, temp.getBody().size());
    }

}
