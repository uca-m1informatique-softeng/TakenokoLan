package serveur.entites;


import commun.entites.Jardinier;
import commun.entites.Panda;
import commun.moteur.Terrain;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActionsJoueurStepDef  {

    private Terrain terrain = new Terrain();
    private Jardinier jardinier = new Jardinier(terrain);
    private Panda panda = new Panda(terrain);
    private Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
    private Parcelle p = new Parcelle(new Coordonnees(1, 0, 1));
    private TestRestTemplate template = new TestRestTemplate();


    private ResponseEntity<Parcelle> response; // output
    private ResponseEntity<Parcelle> response1; // output
    private ResponseEntity<Parcelle> response2; // output
    private ResponseEntity<CartesObjectifs> response3; // output
    private ResponseEntity<ArrayList<Parcelle>> response4; // output



    @Given("^la partie")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @And("^le jardinier")
    public void initLeJardinier() {
        Jardinier jardinier = new Jardinier(terrain);
    }

    @And("^le panda")
    public void initLePanda() {
        Panda panda = new Panda(terrain);
    }

    @And("^un terrain avec des parcelles")
    public void initTerrain(){
        p1.setCouleur(Parcelle.Couleur.VERTE);
        p.setCouleur(Parcelle.Couleur.VERTE);
        terrain.getZoneJouee().put(p.getCoord(), p);
        p.setIrriguee(true);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        p1.setIrriguee(true);
    }


    @When("^le client appelle/PoserParcelle")
    public void poserParcelle() {
        initTerrain();
        response = template.exchange("http://localhost:8080/0/PoserParcelle", HttpMethod.POST, null,
                Parcelle.class);

    }

    @Then("^si c'est possible une parcelle est posée sur le terrain")
    public void parcellePosee() {

        poserParcelle();
        Assert.assertNotEquals(new Coordonnees(0,0,0),response.getBody().getCoord());
        Assert.assertNotEquals(new Coordonnees(1, 0, 1),response.getBody().getCoord());
        Assert.assertNotEquals(new Coordonnees(1, 0, -1),response.getBody().getCoord());

    }

     @When("^le client appelle /DeplacerJardinier")
    public void deplacementJardinier() {
        initTerrain();
        initLeJardinier();
        response1 = template.exchange("http://localhost:8080/0/DeplacerJardinier", HttpMethod.POST, null,
                Parcelle.class);
    }

    @Then("^si c'est possible le jardinier est déplacé sur le terrain")
    public void deplacementJardinierEffectue() {

        deplacementJardinier();
        Assert.assertNotEquals(response1.getBody().getCoord(),new Coordonnees(0,0,0));
    }



    @When("^le client appelle /DeplacerPanda")
    public void deplacementPanda() {
        initTerrain();
        initLePanda();
        response2 = template.exchange("http://localhost:8080/0/DeplacerPanda", HttpMethod.POST, null,
                Parcelle.class);
    }

    @Then("^si c'est possible le panda est déplacé sur le terrain")
    public void deplacementPandaEffectue() {
        deplacementPanda();
        assertNotEquals(response2.getBody().getCoord(),new Coordonnees(0,0,0));
    }



    @When("^le client appelle /PiocheUnObjectif")
    public void piocheObjectif() {
        response3 = template.exchange("http://localhost:8080/0/PiocherUnObjectif", HttpMethod.POST, null,
                CartesObjectifs.class);
    }

    @Then("^return au moins une carte")
    public void returnCard() {

        assertNotEquals(null,response3.getBody());
    }

}
