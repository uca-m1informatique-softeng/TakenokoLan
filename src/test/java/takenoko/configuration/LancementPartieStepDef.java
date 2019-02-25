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
    ArrayList<StatistiqueJoueur> listPlayer;

    @Given("^le terrain")
    public void initTerrain(){
        Terrain terrain = new Terrain();
    }
    @And("^un bot")
    public void initBot()  {
        listPlayer = new ArrayList<>();
        listPlayer.add(1, (StatistiqueJoueur) IA.newIA(IA.Type.PANDA));
    }

    @When("^la partie est en cours")
    public void unePartieEnCours() {
        finPartie=false;
    }
    @Then("^on affiche hello")
    public void afficherHello()  {
        assertEquals("hello",takenoko.nbPartie(1,1,0));
    }
}