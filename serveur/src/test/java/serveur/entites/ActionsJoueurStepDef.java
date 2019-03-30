package serveur.entites;

import commun.moteur.Terrain;
import commun.ressources.Coordonnees;
import commun.ressources.Parcelle;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

public class ActionsJoueurStepDef {

    Terrain terrain = new Terrain();
    private Jardinier jardinier = new Jardinier(terrain);
    private Panda panda = new Panda(terrain);
    private ArrayList<Coordonnees> deplacementPossible = new ArrayList<>();
    Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
    Parcelle p = new Parcelle(new Coordonnees(1, 0, 1));


    private TestRestTemplate template = new TestRestTemplate();
    private ResponseEntity<Parcelle> response; // output
    private ResponseEntity<Coordonnees> response1; // output
    private ResponseEntity<Coordonnees> response2; // output

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
        response = template.exchange("http://localhost:8080/PoserParcelle", HttpMethod.POST, null,
                Parcelle.class);

    }

    @Then("^si c'est possible une parcelle est posée sur le terrain")
    public void parcellePosee() {
        initTerrain();
        poserParcelle();
        Assert.assertEquals(3,terrain.getZoneJouee().size());
        Assert.assertNotEquals(new Coordonnees(0,0,0),response.getBody());

    }


    @When("^le client appelle /DeplacerJardinier")
    public void deplacementJardinier() {
        initTerrain();
        initLeJardinier();
        parcellePosee();
        response1 = template.exchange("http://localhost:8080/DeplacerJardinier", HttpMethod.POST, null,
                Coordonnees.class);
    }

    @Then("^si c'est possible le jardinier est déplacé sur le terrain")
    public void deplacementJardinierEffectue() {
        deplacementJardinier();
        Assert.assertNotEquals(new Coordonnees(0,0,0),response.getBody());
    }



    @When("^le client appelle /DeplacerPanda")
    public void deplacementPanda() {
        initTerrain();
        initLePanda();
        parcellePosee();
        response2 = template.exchange("http://localhost:8080/DeplacerPanda", HttpMethod.POST, null,
                Coordonnees.class);
    }

    @Then("^si c'est possible le panda est déplacé sur le terrain")
    public void deplacementPandaEffectue() {
        assertNotEquals(response.getBody(),new Coordonnees(0,0,0));
    }

}
