package serveur;

import commun.moteur.Terrain;
import commun.ressources.*;
import commun.triche.TricheException;
import org.junit.Assert;
import org.junit.Test;
import commun.entites.Panda;

import java.util.ArrayList;

public class CarteObjectifPandaTest {

    @Test
    public void carteObjectifPandaTest() throws TricheException {
        Terrain terrain = new Terrain();
        Panda panda = new Panda(terrain);
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("bob");
        CarteObjectifPanda doubleJaune = new CarteObjectifPanda(Parcelle.Couleur.JAUNE, 4);
        CarteObjectifPanda doubleVert = new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3);
        CarteObjectifPanda doubleRose = new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5);
        CarteObjectifPanda tripleCouleur = new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE);

        ArrayList<CartesObjectifs> listeCartes = new ArrayList<>();

        Parcelle source = new Parcelle(new Coordonnees(0, 0, 0));
        source.setCouleur(Parcelle.Couleur.SOURCE);
        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
        p2.setCouleur(Parcelle.Couleur.JAUNE);
        Parcelle p3 = new Parcelle(new Coordonnees(1, 0, -1));
        p3.setCouleur(Parcelle.Couleur.VERTE);

        terrain.getZoneJouee().put(source.getCoord(), source);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p3.getCoord(), p3);

        //cas objectif deux bambous roses
        listeCartes.add(doubleRose);
        feuilleJoueur.setMainObjectif(listeCartes);
        p1.pousserBambou();
        p1.pousserBambou();

        panda.deplacerEntite(p1.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p1.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);

        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(5, feuilleJoueur.getPointsBot());

        //cas objectif deux bambous jaunes
        listeCartes.add(doubleJaune);
        feuilleJoueur.setMainObjectif(listeCartes);
        p2.pousserBambou();
        p2.pousserBambou();


        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p1.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);

        terrain.verifObjectifAccompli(feuilleJoueur);
        //on attend les points precedents (5) plus ceux ajoutés (4) donc 9 au total
        Assert.assertEquals(9, feuilleJoueur.getPointsBot());

        //cas objectif deux bambous verts
        listeCartes.add(doubleVert);
        feuilleJoueur.setMainObjectif(listeCartes);
        p3.pousserBambou();
        p3.pousserBambou();


        panda.deplacerEntite(p3.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p3.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);

        terrain.verifObjectifAccompli(feuilleJoueur);
        //on attend les points precedents (9) plus ceux ajoutés (3) donc 12 au total
        Assert.assertEquals(12, feuilleJoueur.getPointsBot());

        //cas objectif triple
        listeCartes.add(tripleCouleur);
        feuilleJoueur.setMainObjectif(listeCartes);
        p1.pousserBambou();
        p2.pousserBambou();
        p3.pousserBambou();

        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p1.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);
        panda.deplacerEntite(p3.getCoord(), feuilleJoueur);
        feuilleJoueur.setPrecedant(15);

        terrain.verifObjectifAccompli(feuilleJoueur);
        //on attend les points precedents (12) plus ceux ajoutés (6) donc 18 au total
        Assert.assertEquals(18, feuilleJoueur.getPointsBot());

    }
}
