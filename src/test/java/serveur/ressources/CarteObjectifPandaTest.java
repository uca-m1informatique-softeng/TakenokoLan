package serveur.ressources;

import commun.ressources.CarteObjectifPanda;
import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.Parcelle;
import org.junit.Assert;
import org.junit.Test;
import serveur.entites.Panda;
import takenoko.ia.IA;
import serveur.iaForTest.IARandom;
import serveur.moteur.Terrain;
import serveur.utilitaires.TricheException;

import java.util.ArrayList;

public class CarteObjectifPandaTest {

    @Test
    public void carteObjectifPandaTest() {
        Terrain terrain = new Terrain();
        IARandom IARandom = new IARandom();
        IARandom.setNomBot("Bob");
        Panda panda = new Panda(terrain);

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
        IARandom.setMainObjectif(listeCartes);
        p1.pousserBambou();
        p1.pousserBambou();

        try {
            panda.deplacerEntite(p1.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p2.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p1.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);

        } catch (TricheException e) {
            e.printStackTrace();
        }
        IA.verifObjectifAccompli(terrain, IARandom);
        Assert.assertEquals(5, IARandom.getFeuilleJoueur().getPointsBot());

        //cas objectif deux bambous jaunes
        listeCartes.add(doubleJaune);
        IARandom.setMainObjectif(listeCartes);
        p2.pousserBambou();
        p2.pousserBambou();

        try {
            panda.deplacerEntite(p2.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p1.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p2.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);

            IA.verifObjectifAccompli(terrain, IARandom);
            //on attend les points precedents (5) plus ceux ajoutés (4) donc 9 au total
            Assert.assertEquals(9, IARandom.getFeuilleJoueur().getPointsBot());

            //cas objectif deux bambous verts
            listeCartes.add(doubleVert);
            IARandom.setMainObjectif(listeCartes);
            p3.pousserBambou();
            p3.pousserBambou();


            panda.deplacerEntite(p3.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p2.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p3.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);

            IA.verifObjectifAccompli(terrain, IARandom);
            //on attend les points precedents (9) plus ceux ajoutés (3) donc 12 au total
            Assert.assertEquals(12, IARandom.getFeuilleJoueur().getPointsBot());

            //cas objectif triple
            listeCartes.add(tripleCouleur);
            IARandom.setMainObjectif(listeCartes);
            p1.pousserBambou();
            p2.pousserBambou();
            p3.pousserBambou();

            panda.deplacerEntite(p2.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p1.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p2.getCoord(), IARandom.getFeuilleJoueur());
            IARandom.getFeuilleJoueur().setPrecedant(15);
            panda.deplacerEntite(p3.getCoord(), IARandom.getFeuilleJoueur());


        } catch (TricheException e) {
            e.printStackTrace();
        }
        IA.verifObjectifAccompli(terrain, IARandom);
        //on attend les points precedents (12) plus ceux ajoutés (6) donc 18 au total
        Assert.assertEquals(18, IARandom.getFeuilleJoueur().getPointsBot());

    }
}
