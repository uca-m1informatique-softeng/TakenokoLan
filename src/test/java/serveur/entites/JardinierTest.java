package serveur.entites;

import org.junit.Assert;
import org.junit.Test;
import serveur.moteur.Terrain;
import commun.ressources.Coordonnees;
import serveur.ressources.FeuilleJoueur;
import commun.ressources.Parcelle;
import serveur.utilitaires.TricheException;

import java.util.ArrayList;

public class JardinierTest {
    private Terrain terrain = new Terrain();
    private Jardinier jardinier = new Jardinier(terrain);
    private ArrayList<Coordonnees> deplacementPossible = new ArrayList<>();

    @Test
    public void JardinierTest() {
        Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
        Parcelle p2 = new Parcelle(new Coordonnees(2, -1, -1));
        Parcelle p3 = new Parcelle(new Coordonnees(3, -2, -1));

        Parcelle p4 = new Parcelle(new Coordonnees(1, -1, 0));
        Parcelle p5 = new Parcelle(new Coordonnees(2, -2, 0));
        Parcelle p6 = new Parcelle(new Coordonnees(3, -3, 0));

        Parcelle p7 = new Parcelle(new Coordonnees(1, -2, 1));
        Parcelle p8 = new Parcelle(new Coordonnees(2, -3, 1));

        p1.setCouleur(Parcelle.Couleur.VERTE);
        p2.setCouleur(Parcelle.Couleur.VERTE);
        p2.setEffet(Parcelle.Effet.PUIT);
        p3.setCouleur(Parcelle.Couleur.VERTE);
        p4.setCouleur(Parcelle.Couleur.VERTE);
        p5.setCouleur(Parcelle.Couleur.VERTE);
        p6.setCouleur(Parcelle.Couleur.VERTE);
        p7.setCouleur(Parcelle.Couleur.VERTE);
        p8.setCouleur(Parcelle.Couleur.VERTE);

        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p3.getCoord(), p3);
        terrain.getZoneJouee().put(p4.getCoord(), p4);
        terrain.getZoneJouee().put(p5.getCoord(), p5);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        deplacementPossible = jardinier.getDeplacementsPossible(terrain.getZoneJouee());

        p1.setIrriguee(true);
        p2.setIrriguee(true);
        p3.setIrriguee(true);
        p4.setIrriguee(true);
        p5.setIrriguee(true);
        p6.setIrriguee(true);
        p7.setIrriguee(true);
        p8.setIrriguee(true);

        int i = deplacementPossible.indexOf(p5.getCoord());
        try {
            jardinier.deplacerEntite(deplacementPossible.get(i), new FeuilleJoueur(""));
        }catch (TricheException e){
            e.printStackTrace();
        }

        Assert.assertEquals(jardinier.getCoordonnees(), deplacementPossible.get(i));

        //loin du jardinier a au moin 2 parcelles de distance
        //1 bambou car le bambou pousse quand la case est posée
        Assert.assertEquals(1, p1.getBambou());

        verifPousser(p2);
        verifPousser(p3);
        verifPousser(p4);
        verifPousser(p5);
        verifPousser(p6);
        verifPousser(p7);
        verifPousser(p8);
    }

    private void verifPousser(Parcelle p) {
        // +1 bambou quand c'est irriguee
        if (p.getEffet() == Parcelle.Effet.PUIT) {
            Assert.assertEquals(3, p.getBambou());
        } else {
            Assert.assertEquals(2, p.getBambou());
        }
    }
}
