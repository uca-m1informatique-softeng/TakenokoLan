package takenoko.entites;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import takenoko.moteur.Terrain;
import takenoko.ressources.Coordonnees;
import takenoko.ressources.FeuilleJoueur;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;

public class FairePousserBambousStepDef {

    Terrain terrain = new Terrain();
    private Jardinier jardinier = new Jardinier(terrain);
    private ArrayList<Coordonnees> deplacementPossible = new ArrayList<>();
    Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
    Parcelle p = new Parcelle(new Coordonnees(1, 0, 1));

    @Given("^un terrain avec des parcelles irriguées")
    public void initTerrain() {

        p1.setCouleur(Parcelle.Couleur.VERTE);
        p.setCouleur(Parcelle.Couleur.VERTE);
    }

    @And("^un jardinier")
    public void initJardinier() {
        Jardinier jardinier = new Jardinier(terrain);
    }

    @When("^on pose d'une parcelle")
    public void poseParcelle() {
        initTerrain();
        terrain.getZoneJouee().put(p.getCoord(), p);
        p.setIrriguee(true);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        p1.setIrriguee(true);

    }

    @Then("^il y a un seul bambou")
    public void nbBambou() {
        initTerrain();
        Assert.assertEquals(1, p.getBambou());
    }


    @When("^le jardinier se déplace sur une parcelle")
    public void deplacementJardinier() {
        initTerrain();
        initJardinier();
        poseParcelle();
        deplacementPossible = jardinier.getDeplacementsPossible(terrain.getZoneJouee());
        int i = deplacementPossible.indexOf(p1.getCoord());
        try {
            jardinier.deplacerEntite(deplacementPossible.get(i), new FeuilleJoueur(""));
        } catch (TricheException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(jardinier.getCoordonnees(), deplacementPossible.get(i));

    }

    @Then("^le nombre de bambous augmente")
    public void nbBambous() {
        Assert.assertEquals(2, p1.getBambou());
    }
}
