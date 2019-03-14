package takenoko.ia;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import takenoko.configuration.Takenoko;
import takenoko.entites.Entite;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.*;
import takenoko.service.impl.ClientService;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.Mockito.when;


public class IAPandaTest {
    private static final int TAILLE_MAIN_MAX = 5;

    //Parcelle & Objectif
    @Test
    public void IAPandaTest1() {
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IAPanda");
        Terrain terrain = new Terrain();
        LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();
        Jardinier jardinier = new Jardinier(terrain);
        Panda panda = new Panda(terrain);
        LaPiocheParcelle laPiocheParcelle = new LaPiocheParcelle();
        FeuilleJoueur feuilleJoueur= new FeuilleJoueur("IAPanda");

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(),1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(feuilleJoueur.getActionChoisie(),0,0,1,1);
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);

        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //IA Panda a bien jouer une parcelle
        Assert.assertEquals(2, terrain.getZoneJouee().size());
        //a bien piocher une carte
        Assert.assertEquals(1, IAPanda.getMainObjectif().size());
        //de type panda
        Assert.assertTrue(verifPanda(IAPanda.getMainObjectif().get(0)));
    }

    //Parcelle & Panda
    @Test
    public void IAPandaTest2() throws TricheException {
        Takenoko takenoko= new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur= new FeuilleJoueur("IAPanda");

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        IAPanda.setMainObjectif(cartesObjectifs);

        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);

        terrain.changements(p1, new FeuilleJoueur(""));

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(),1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,0,0,2,2);
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda a bien jouer une parcelle
        Assert.assertEquals(3, terrain.getZoneJouee().size());
        //IA Panda a bien jouer le panda
        Assert.assertEquals(new Coordonnees(-1, 1, 0), panda.getCoordonnees());

    }

    //Parcelle & Jardinier
    @Test
    public void IAPandaTest3()throws TricheException {
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = new Terrain();
        LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();
        Jardinier jardinier = new Jardinier(terrain);
        Panda panda = new Panda(terrain);
        LaPiocheParcelle laPiocheParcelle = new LaPiocheParcelle();
        FeuilleJoueur feuilleJoueur= new FeuilleJoueur("IAPanda");

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        IAPanda.setMainObjectif(cartesObjectifs);

        Parcelle p2 = new Parcelle(new Coordonnees(-1, 1, 0));
        p2.setCouleur(Parcelle.Couleur.ROSE);
        terrain.changements(p2, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(-1, 1, 0)).mangerBambou();

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(),1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,0,0,3,3);

        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //IA Panda a bien jouer une parcelle
        Assert.assertEquals(3, terrain.getZoneJouee().size());
        //IA Panda a bien jouer le jardinier
        if (verifPoseParcelleRose(terrain)) {
            Assert.assertEquals(Parcelle.Couleur.ROSE, terrain.getZoneJouee().get(jardinier.getCoordonnees()).getCouleur());
        } else {
            Assert.assertEquals(Parcelle.Couleur.ROSE, terrain.getZoneJouee().get(jardinier.getCoordonnees()).getCouleur());
        }
    }

    // Panda & Jardinier + Panda & Objectif
    @Test
    public void IAPandaTest4()throws TricheException {
        Takenoko takenoko= new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur= new FeuilleJoueur("");

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        IAPanda.setMainObjectif(cartesObjectifs);

        Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
        p1.setCouleur(Parcelle.Couleur.JAUNE);
        terrain.changements(p1, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(1, 0, -1)).mangerBambou();

        Parcelle p2 = new Parcelle(new Coordonnees(-1, 1, 0));
        p2.setCouleur(Parcelle.Couleur.ROSE);
        terrain.changements(p2, new FeuilleJoueur(""));

        Parcelle p3 = new Parcelle(new Coordonnees(0, 1, -1));
        p3.setCouleur(Parcelle.Couleur.VERTE);
        terrain.changements(p3, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(0, 1, -1)).mangerBambou();
        try {
            jardinier.deplacerEntite(p1.getCoord(), new FeuilleJoueur(""));
            panda.deplacerEntite(p1.getCoord(), new FeuilleJoueur(""));
        } catch (TricheException e) {
            e.printStackTrace();
        }
        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(),1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,3,3);
        //essaye de rejoindre p2
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda n'a pas poser de parcelle (3 poser plus haut + la source)
        Assert.assertEquals(4, terrain.getZoneJouee().size());

        //IA Panda a bien jouer le panda
        Assert.assertTrue(verifDeplacementCoude(jardinier, p3));
        //IA Panda a bien jouer le jardinier
        Assert.assertTrue(verifDeplacementCoude(jardinier, p3));

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2,1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,2,3,3,3,3);

        //est sur p2
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda a bien jouer le panda
        Assert.assertEquals(p2.getCoord(), panda.getCoordonnees());
        //IA Panda a bien jouer le jardinier
        Assert.assertEquals(p2.getCoord(), jardinier.getCoordonnees());

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2,1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,2,3,3,3,3);

        //ne pouvant pas aller sur p2 il essaye de deplacer au plus proche du centre soit ici 0,0,0
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda a bien jouer le panda
        Assert.assertEquals(new Coordonnees(0, 0, 0), panda.getCoordonnees());
        //IA Panda a bien jouer le jardinier
        Assert.assertEquals(new Coordonnees(0, 0, 0), jardinier.getCoordonnees());

        p2.pousserBambou();
        p2.pousserBambou();
        p2.pousserBambou();

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2,1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,2,1,1);
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda a bien jouer le panda
        Assert.assertEquals(p2.getCoord(), panda.getCoordonnees());

        Assert.assertEquals(5, IAPanda.getFeuilleJoueur().getPointsBot());
        //IA Panda a pioche une carte objectif
        Assert.assertEquals(TAILLE_MAIN_MAX, IAPanda.getMainObjectif().size());
    }

    //reussi un objectif une couleur de chaque quand le terrain est en place
    @Test
    public void IAPandaTest5() throws TricheException{
        Takenoko takenoko= new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur= new FeuilleJoueur("");

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        IAPanda.setMainObjectif(cartesObjectifs);

        Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
        p1.setCouleur(Parcelle.Couleur.JAUNE);
        terrain.changements(p1, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(1, 0, -1)).mangerBambou();

        Parcelle p2 = new Parcelle(new Coordonnees(-1, 1, 0));
        p2.setCouleur(Parcelle.Couleur.ROSE);
        terrain.changements(p2, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(1, 0, -1)).mangerBambou();

        Parcelle p3 = new Parcelle(new Coordonnees(0, 1, -1));
        p3.setCouleur(Parcelle.Couleur.VERTE);
        terrain.changements(p3,new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(0, 1, -1)).mangerBambou();

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2,1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,2,3,3,3,3);
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2,1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,2,3,3,3,3);
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2,1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0,2,2,2,3,3,3,3);
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //reussi un objectif une couleur de chaque
        Assert.assertEquals(6, IAPanda.getFeuilleJoueur().getPointsBot());
    }

    private boolean verifDeplacementCoude(Entite entite, Parcelle pAdjacente) {
        if (entite.getCoordonnees().equals(new Coordonnees(0, 0, 0)) || entite.getCoordonnees().equals(pAdjacente.getCoord())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verifPanda(CartesObjectifs c) {
        return c instanceof CarteObjectifPanda;
    }

    private boolean verifPoseParcelleRose(Terrain terrain) {
        int compt = 0;
        for (Map.Entry<Coordonnees, Parcelle> entry : terrain.getZoneJouee().entrySet()) {
            Parcelle valeur = entry.getValue();
            if (valeur.getCouleur() == Parcelle.Couleur.ROSE) {
                compt++;
            }
        }
        if (compt == 1) {
            return true;
        } else {
            return false;
        }


    }

    //favorise un objectif pouvant etre fini sur cette action
    @Test
    public void IAPandaTest6() throws TricheException{
        Takenoko takenoko= new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur= new FeuilleJoueur("");

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);


        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.JAUNE, 4));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3));
        IAPanda.setMainObjectif(cartesObjectifs);

        IAPanda.getFeuilleJoueur().incBambouJaune();

        Parcelle p1 = new Parcelle(new Coordonnees(1, 0, -1));
        p1.setCouleur(Parcelle.Couleur.JAUNE);
        terrain.changements(p1, new FeuilleJoueur(""));

        Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
        p2.setCouleur(Parcelle.Couleur.ROSE);
        terrain.changements(p2, new FeuilleJoueur(""));

        Parcelle p3 = new Parcelle(new Coordonnees(-1, 1, 0));
        p3.setCouleur(Parcelle.Couleur.VERTE);
        terrain.changements(p3, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(-1, 1, 0)).mangerBambou();

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        //IA Panda va faire l'objectif JAUNE car il peut le finir sur ce tour
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //reussi l'objectif JAUNE
        Assert.assertEquals(4, IAPanda.getFeuilleJoueur().getPointsBot());
    }
}

