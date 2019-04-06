package serveur.configuration;

import commun.moteur.Terrain;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import serveur.utilitaires.StatistiqueJoueur;

import java.util.ArrayList;

public class LancementPartieStepDef {

    Takenoko takenoko = new Takenoko();
    boolean finPartie = false;
    ArrayList<StatistiqueJoueur> listPlayer;

    @Given("^le terrain")
    public void initTerrain() {
        Terrain terrain = new Terrain();
    }

    @And("^un bot")
    public void initBot() {
        listPlayer = new ArrayList<>();/*
        listPlayer.add((StatistiqueJoueur) IA.newIA(IA.Type.PANDA));*/
    }

    @When("^la partie est en cours")
    public void unePartieEnCours() {
        finPartie = false;
    }

    @Then("^on affiche hello")
    public void afficherHello() {/*
        assertEquals("hello", takenoko.nbPartie(1, 1, 0));*/
    }
    
}