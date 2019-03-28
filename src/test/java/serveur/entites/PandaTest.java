package serveur.entites;

import org.junit.Assert;
import org.junit.Test;
import serveur.moteur.Terrain;
import commun.ressources.Coordonnees;
import serveur.ressources.FeuilleJoueur;
import commun.ressources.Parcelle;
import serveur.utilitaires.TricheException;

import java.util.ArrayList;

public class PandaTest {

    @Test
    public void pandaTest() {
        Terrain terrain = new Terrain();
        Panda panda = new Panda(terrain);

        Assert.assertEquals(0, panda.getCoordonnees().getX());
        Assert.assertEquals(0, panda.getCoordonnees().getY());
        Assert.assertEquals(0, panda.getCoordonnees().getZ());

        Parcelle source = new Parcelle(new Coordonnees(0, 0, 0));
        source.setCouleur(Parcelle.Couleur.SOURCE);
        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
        p2.setCouleur(Parcelle.Couleur.JAUNE);
        Parcelle p3 = new Parcelle(new Coordonnees(1, 0, -1));
        p3.setCouleur(Parcelle.Couleur.VERTE);
        Parcelle p4 = new Parcelle(new Coordonnees(1, -1, 0));
        Parcelle p5 = new Parcelle(new Coordonnees(0, -1, 1));
        Parcelle p6 = new Parcelle(new Coordonnees(-1, 0, 1));
        Parcelle p7 = new Parcelle(new Coordonnees(1, 1, -2));
        Parcelle p8 = new Parcelle(new Coordonnees(2, 0, -1));

        //test pousser bambou

        Assert.assertFalse(p1.mangerBambou());
        p1.pousserBambou();
        Assert.assertEquals(1, p1.getBambou());
        p1.pousserBambou();
        p1.pousserBambou();
        p1.pousserBambou();
        Assert.assertEquals(3, p1.getBambou());

        p2.pousserBambou();
        p3.pousserBambou();
        p4.pousserBambou();

        terrain.getZoneJouee().put(source.getCoord(), source);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p3.getCoord(), p3);
        terrain.getZoneJouee().put(p4.getCoord(), p4);
        terrain.getZoneJouee().put(p5.getCoord(), p5);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);

        ArrayList<Coordonnees> deplacementPossible;
        deplacementPossible = panda.getDeplacementsPossible(terrain.getZoneJouee());

        try {
            panda.deplacerEntite(deplacementPossible.get(getPosition(p1.getCoord(), deplacementPossible)), new FeuilleJoueur(""));
            Assert.assertEquals(2, p1.getBambou());

            deplacementPossible = panda.getDeplacementsPossible(terrain.getZoneJouee());

            panda.deplacerEntite(deplacementPossible.get(getPosition(p2.getCoord(), deplacementPossible)), new FeuilleJoueur(""));
            Assert.assertEquals(0, p2.getBambou());

            deplacementPossible = panda.getDeplacementsPossible(terrain.getZoneJouee());

            panda.deplacerEntite(deplacementPossible.get(getPosition(source.getCoord(), deplacementPossible)), new FeuilleJoueur(""));
            Assert.assertEquals(0, source.getBambou());

            deplacementPossible = panda.getDeplacementsPossible(terrain.getZoneJouee());

            panda.deplacerEntite(deplacementPossible.get(getPosition(p2.getCoord(), deplacementPossible)), new FeuilleJoueur(""));
            Assert.assertEquals(0, p2.getBambou());


        } catch (TricheException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("trouve pas la coordonnee voulue");
            e.getMessage();
        }
    }

    private int getPosition(Coordonnees coordonnees, ArrayList<Coordonnees> deplacementsPossible) {

        for (Coordonnees c : deplacementsPossible) {
            if (c.equals(coordonnees)) {
                return deplacementsPossible.indexOf(c);
            }
        }

        return -1;
    }
}
