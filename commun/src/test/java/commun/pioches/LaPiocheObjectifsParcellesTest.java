package commun.pioches;

import commun.moteur.Terrain;
import commun.ressources.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class LaPiocheObjectifsParcellesTest {
    private Terrain terrain = new Terrain();

    @Test
    public void LaPiocheObjectifParcellesTest() {
        LaPiocheObjectifsParcelles laPiocheObjectifsParcelles = new LaPiocheObjectifsParcelles();

        // test pioche objectifs motif triangle
        Assert.assertEquals(15, laPiocheObjectifsParcelles.getPioche().size());

        laPiocheObjectifsParcelles.piocher();

        Assert.assertEquals(14, laPiocheObjectifsParcelles.getPioche().size());

        //test d'une carte a plusieurs couleurs et de son toString (a enlever surement plus tard)
        CarteObjectifParcelle carte = new CarteObjectifParcelle(CarteObjectifParcelle.Motif.TRIANGLE, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, 6);
        //System.out.println(carte.toString());

        laPiocheObjectifsParcelles.getPioche().add(carte);
        Assert.assertEquals(15, laPiocheObjectifsParcelles.getPioche().size());

        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
        Parcelle p6 = new Parcelle(new Coordonnees(-1, 0, 1));

        // OBJECTIF PARCELLE
        // TRIANGLE
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("bob");
        
        CarteObjectifParcelle carteObjectifParcelleTriangle = new CarteObjectifParcelle(CarteObjectifParcelle.Motif.TRIANGLE, Parcelle.Couleur.VERTE, 2);
        CarteObjectifParcelle carteObjectifParcelleLigne = new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LIGNE, Parcelle.Couleur.JAUNE, 3);
        CarteObjectifParcelle carteObjectifParcelleCourbe = new CarteObjectifParcelle(CarteObjectifParcelle.Motif.COURBE, Parcelle.Couleur.VERTE, 2);
        CarteObjectifParcelle carteObjectifParcelleLosange1 = new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.VERTE, 3);
        CarteObjectifParcelle carteObjectifParcelleLosange2 = new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.VERTE, Parcelle.Couleur.JAUNE, 3);

        ArrayList<CartesObjectifs> listeCartes = new ArrayList<>();
        Parcelle p7 = new Parcelle(new Coordonnees(-1, 2, -1));
        Parcelle p8 = new Parcelle(new Coordonnees(-2, 2, 0));
        Parcelle p9 = new Parcelle(new Coordonnees(-2, 1, 1));
        p1.setCouleur(Parcelle.Couleur.VERTE);
        p7.setCouleur(Parcelle.Couleur.VERTE);
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p9.setCouleur(Parcelle.Couleur.VERTE);
        p1.setIrriguee(true);
        p2.setIrriguee(true);
        p6.setIrriguee(true);
        p7.setIrriguee(true);
        p8.setIrriguee(true);
        p9.setIrriguee(true);
        // cas G-HG
        listeCartes.add(carteObjectifParcelleTriangle);
        feuilleJoueur.setMainObjectif(listeCartes);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif TRIANGLE G-HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-HD
        listeCartes.add(carteObjectifParcelleTriangle);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif TRIANGLE HG-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D
        listeCartes.add(carteObjectifParcelleTriangle);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif TRIANGLE HD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-BD
        listeCartes.add(carteObjectifParcelleTriangle);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif TRIANGLE D-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BD-BG
        listeCartes.add(carteObjectifParcelleTriangle);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif TRIANGLE BD-BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-G
        listeCartes.add(carteObjectifParcelleTriangle);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif TRIANGLE BG-G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // LIGNE
        Parcelle p10 = new Parcelle(new Coordonnees(-3, 3, 0));
        p10.setIrriguee(true);
        p1.setCouleur(Parcelle.Couleur.JAUNE);
        p2.setCouleur(Parcelle.Couleur.JAUNE);
        p6.setCouleur(Parcelle.Couleur.JAUNE);
        p7.setCouleur(Parcelle.Couleur.JAUNE);
        p9.setCouleur(Parcelle.Couleur.JAUNE);
        p8.setCouleur(Parcelle.Couleur.JAUNE);
        p10.setCouleur(Parcelle.Couleur.JAUNE);

        // cas D-DD
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p10.getCoord(), p10);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LIGNE D-D/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-D
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p10.getCoord(), p10);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LIGNE G-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-GG
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p10.getCoord(), p10);
        //System.out.println("Test de la détection de motif LIGNE G-G/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-HDHD
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LIGNE HD-HD/HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-HD
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LIGNE BG-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BGBG
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LIGNE BG-BG/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-HGHG
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif LIGNE HG-HG/HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-BD
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif LIGNE HG-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BD-BDBD
        listeCartes.add(carteObjectifParcelleLigne);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LIGNE BD-BD/BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // COURBE
        p1.setCouleur(Parcelle.Couleur.VERTE);
        p2.setCouleur(Parcelle.Couleur.VERTE);
        p6.setCouleur(Parcelle.Couleur.VERTE);
        p7.setCouleur(Parcelle.Couleur.VERTE);
        p9.setCouleur(Parcelle.Couleur.VERTE);
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p10.setCouleur(Parcelle.Couleur.VERTE);

        // cas HD-HG/HD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif COURBE  HD-HG/HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-BD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif COURBE HD-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD/BG
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif COURBE BG-BD/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-HD/D
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif COURBE D-HD/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif COURBE G-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-G/BG
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif COURBE BG-G/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-BD/D
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif COURBE D-BD/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif COURBE G-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-G/HG
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif COURBE HG-G/HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BD-D/BD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif COURBE BD-D/BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-D
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif COURBE HG-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HG/G
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif COURBE G-HG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-G/BG
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif COURBE BG-G/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif COURBE G-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-HD/D
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif COURBE D-HD/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D/HD
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif COURBE HD-D/HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-D
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif COURBE BG-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG/G
        listeCartes.add(carteObjectifParcelleCourbe);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif COURBE G-BG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(2, feuilleJoueur.getPointsBot());
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
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HG-HD-HD/HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D-BD
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HD-D-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-G-BD
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HG-G-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD-BD/BG
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE monochrome BG-BD-BD/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D-D/HD
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HD-D-D/HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD-D
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE monochrome BG-BD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HD-HD
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-HD-HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG-BG/G
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-BG-BG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-BD-BD/D
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif LOSANGE monochrome D-BD-BD/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG-BD
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-BG-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-HD-D
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE monochrome HG-HD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HG-HG/G
        listeCartes.add(carteObjectifParcelleLosange1);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE monochrome G-HG-HG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // LOSANGE DUOCHROME
        p1.setCouleur(Parcelle.Couleur.JAUNE);
        p7.setCouleur(Parcelle.Couleur.JAUNE);
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p9.setCouleur(Parcelle.Couleur.VERTE);

        // cas HG-HD-HD/HG
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE duochrome HG-HD-HD/HG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D-BD
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE duochrome HD-D-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-G-BG
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        //System.out.println("Test de la détection de motif LOSANGE duochrome HG-G-BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD-BD/BG
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE duochrome BG-BD-BD/BG :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HD-D-D/HD
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p7.setCouleur(Parcelle.Couleur.VERTE);
        p1.setCouleur(Parcelle.Couleur.JAUNE);
        p2.setCouleur(Parcelle.Couleur.JAUNE);
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE duochrome HD-D-D/HD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas BG-BD-D
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE duochrome BG-BD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HG-BD
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        //System.out.println("Test de la détection de motif LOSANGE duochrome G-HG-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG-BG/G
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p2.getCoord(), p2);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p7.getCoord(), p7);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        //System.out.println("Test de la détection de motif LOSANGE duochrome G-BG-BG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas D-BD-BD/D
        p8.setCouleur(Parcelle.Couleur.VERTE);
        p1.setCouleur(Parcelle.Couleur.VERTE);
        p6.setCouleur(Parcelle.Couleur.JAUNE);
        p9.setCouleur(Parcelle.Couleur.JAUNE);
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE duochrome D-BD-BD/D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-BG-BD
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE duochrome G-BG-BD :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas HG-HD-D
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        //System.out.println("Test de la détection de motif LOSANGE duochrome HG-HD-D :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

        // cas G-HG-HG/G
        listeCartes.add(carteObjectifParcelleLosange2);
        feuilleJoueur.setMainObjectif(listeCartes);
        terrain.getZoneJouee().put(p6.getCoord(), p6);
        terrain.getZoneJouee().put(p8.getCoord(), p8);
        terrain.getZoneJouee().put(p1.getCoord(), p1);
        terrain.getZoneJouee().put(p9.getCoord(), p9);
        //System.out.println("Test de la détection de motif LOSANGE duochrome G-HG-HG/G :");
        terrain.verifObjectifAccompli(feuilleJoueur);
        Assert.assertEquals(3, feuilleJoueur.getPointsBot());
        terrain.getZoneJouee().clear();
        feuilleJoueur.setPointsBot(0);

    }
}
