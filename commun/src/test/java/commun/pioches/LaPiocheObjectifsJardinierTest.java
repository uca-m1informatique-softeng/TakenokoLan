package commun.pioches;

import commun.moteur.Terrain;
import commun.ressources.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class LaPiocheObjectifsJardinierTest {
    private Terrain terrain = new Terrain();

    @Test
    public void LaPiocheObjectifsJardinierTest() {
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("Pierre");
        LaPiocheObjectifsJardinier laPiocheObjectifsJardinier = new LaPiocheObjectifsJardinier();

        Assert.assertEquals(15, laPiocheObjectifsJardinier.getPioche().size());

        laPiocheObjectifsJardinier.piocher();
        Assert.assertEquals(14, laPiocheObjectifsJardinier.getPioche().size());
        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 1));
        p1.setCoord(new Coordonnees(-1, 1, 0));
        Assert.assertEquals(new Coordonnees(-1, 1, 0), p1.getCoord());
        Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
        ArrayList<CartesObjectifs> listeCartes = new ArrayList<>();

        p1.setCouleur(Parcelle.Couleur.VERTE);
        p2.setCouleur(Parcelle.Couleur.VERTE);

        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);

        CarteObjectifJardinier carteObjectifParcelle = new CarteObjectifJardinier(3, Parcelle.Couleur.VERTE, 3, CartesObjectifs.Motif.POINT);
        CarteObjectifJardinier carteObjectifParcelle2 = new CarteObjectifJardinier(3, Parcelle.Couleur.VERTE, 3, CartesObjectifs.Motif.LIGNE);
        CarteObjectifJardinier carteObjectifParcelle3 = new CarteObjectifJardinier(3, Parcelle.Couleur.VERTE, 3, CartesObjectifs.Motif.TRIANGLE);
        CarteObjectifJardinier carteObjectifParcelle31 = new CarteObjectifJardinier(3, Parcelle.Couleur.JAUNE, 3, CartesObjectifs.Motif.TRIANGLE);
        CarteObjectifJardinier carteObjectifParcelle4 = new CarteObjectifJardinier(3, Parcelle.Couleur.VERTE, 3, CartesObjectifs.Motif.LOSANGE);
        CarteObjectifJardinier carteObjectifParcelle41 = new CarteObjectifJardinier(3, Parcelle.Couleur.JAUNE, 3, CartesObjectifs.Motif.LOSANGE);
        listeCartes.add(carteObjectifParcelle);
        listeCartes.add(carteObjectifParcelle2);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);

        for (int i = 0; i < 3; i++) {
            p1.pousserBambou();
            p2.pousserBambou();
        }
        Assert.assertEquals(p1.getBambou(), 3);
        //Bambou de taille 3
        Assert.assertEquals(p1.getBambou(), carteObjectifParcelle.getBambou());
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(6, feuilleJoueur.getPointsBot());
        Assert.assertEquals(2, feuilleJoueur.getNbObjectifsValide());
        Parcelle p6 = new Parcelle(new Coordonnees(-1, 0, 1));
        Parcelle p7 = new Parcelle(new Coordonnees(-1, 2, -1));
        Parcelle p8 = new Parcelle(new Coordonnees(-2, 2, 0));
        Parcelle p9 = new Parcelle(new Coordonnees(-2, 1, 1));
        Parcelle p10 = new Parcelle(new Coordonnees(-3, 3, 0));
        p7.setCouleur(Parcelle.Couleur.VERTE);
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p9.setCouleur(Parcelle.Couleur.VERTE);

        p1.setIrriguee(true);
        p2.setIrriguee(true);
        p6.setIrriguee(true);
        p7.setIrriguee(true);
        p8.setIrriguee(true);
        p9.setIrriguee(true);
        p10.setIrriguee(true);

        for (int i = 0; i < 3; i++) {
            p6.pousserBambou();
            p7.pousserBambou();
            p8.pousserBambou();
            p9.pousserBambou();
            p10.pousserBambou();
        }
        //triangle avec 3 bambou
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);
        // cas G-HG
        listeCartes.add(carteObjectifParcelle3);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        // //System.out.println("Test de la détection de motif TRIANGLE G-HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle31);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-HD
        listeCartes.add(carteObjectifParcelle3);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        ////System.out.println("Test de la détection de motif TRIANGLE HG-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle31);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D
        listeCartes.add(carteObjectifParcelle3);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif TRIANGLE HD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle31);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-BD
        listeCartes.add(carteObjectifParcelle3);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif TRIANGLE D-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle31);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BD-BG
        listeCartes.add(carteObjectifParcelle3);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif TRIANGLE BD-BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle31);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-G
        listeCartes.add(carteObjectifParcelle3);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif TRIANGLE BG-G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle31);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // LOSANGE MONOCHROME
        p1.setCouleur(Parcelle.Couleur.VERTE);
        p2.setCouleur(Parcelle.Couleur.VERTE);
        p6.setCouleur(Parcelle.Couleur.VERTE);
        p7.setCouleur(Parcelle.Couleur.VERTE);
        p9.setCouleur(Parcelle.Couleur.VERTE);
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p10.setCouleur(Parcelle.Couleur.VERTE);

        // cas HG-HD-HD/HG
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HG-HD-HD/HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D-BD
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HD-D-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-G-BD
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HG-G-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD-BD/BG
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE monochrome BG-BD-BD/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D-D/HD
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HD-D-D/HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD-D
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE monochrome BG-BD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HD-HD
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-HD-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG-BG/G
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-BG-BG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-BD-BD/D
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif LOSANGE monochrome D-BD-BD/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG-BD
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-BG-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-HD-D
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HG-HD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HG-HG/G
        listeCartes.add(carteObjectifParcelle4);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-HG-HG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        //cas ou ya pas
        listeCartes.add(carteObjectifParcelle41);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());

        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);
    }
}
