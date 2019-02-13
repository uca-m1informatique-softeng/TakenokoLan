package takenoko.configuration;

import cucumber.api.java.en.*;
import takenoko.ia.IA;
import takenoko.moteur.Terrain;
import takenoko.utilitaires.StatistiqueJoueur;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class LancementPartieStepDef {

    Takenoko takenoko = new Takenoko();
    boolean finPartie = false;

    @Given("^un terrain")
    public void initTerrain() throws Throwable {
        Terrain terrain = new Terrain();
    }
    @And("^un bot")
    public void initBot() throws Throwable {
        ArrayList<StatistiqueJoueur> listPlayer = new ArrayList<>();
        listPlayer.add(1, (StatistiqueJoueur) IA.newIA(IA.Type.PANDA));
    }

    @When("^la partie est en cours")
    public void unePartieEnCours() throws Throwable {
        finPartie=false;
    }
    @Then("^on affiche hello")
    public void afficherHello() throws Throwable {
        assertEquals("hello",takenoko.nbPartie(1,0,1));
    }
}