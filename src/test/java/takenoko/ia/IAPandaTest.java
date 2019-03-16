package takenoko.ia;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


public class IAPandaTest {
    private static final int TAILLE_MAIN_MAX = 5;

    //Parcelle & Objectif
    @Test
    public void IAPandaTest1() throws TricheException {
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IAPanda");
        Terrain terrain = new Terrain();
        LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();
        Jardinier jardinier = new Jardinier(terrain);
        Panda panda = new Panda(terrain);
        LaPiocheParcelle laPiocheParcelle = new LaPiocheParcelle();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("IAPanda");

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);


        laPiocheParcelle.getPioche().clear();
        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        laPiocheParcelle.getPioche().add(p1);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 2);
                        return null;
                    }
                }).when(iService).piocherUnObjectif(2);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        terrain.changements(p1, IAPanda.getFeuilleJoueur());
                        return null;
                    }
                }).when(iService).poserParcelle(p1);
        when(iService.piocheParcelleIsEmpty()).thenReturn(false);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(), 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(feuilleJoueur.getActionChoisie(), 0, 0, 1, 1);
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);


        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //IA Panda a bien jouer une parcelle
        Assert.assertEquals(2, terrain.getZoneJouee().size());
        //a bien piocher une carte
        Assert.assertEquals(1, IAPanda.getFeuilleJoueur().getMainObjectif().size());
        //de type panda
        Assert.assertTrue(verifPanda(IAPanda.getFeuilleJoueur().getMainObjectif().get(0)));
    }

    //Parcelle & Panda
    @Test
    public void IAPandaTest2() throws TricheException {
        Takenoko takenoko = new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("IAPanda");

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        feuilleJoueur.setMainObjectif(cartesObjectifs);

        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);

        terrain.changements(p1, new FeuilleJoueur(""));

        laPiocheParcelle.getPioche().clear();
        Parcelle p2 = new Parcelle(new Coordonnees(1, 1, 0));
        p2.setCouleur(Parcelle.Couleur.JAUNE);
        laPiocheParcelle.getPioche().add(p2);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        terrain.changements(p2, IAPanda.getFeuilleJoueur());
                        return null;
                    }
                }).when(iService).poserParcelle(p2);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(), 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 0, 0, 0, 0, 2, 2);
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(new Coordonnees(-1, 1, 0), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(new Coordonnees(-1, 1, 0));

        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda a bien jouer une parcelle
        Assert.assertEquals(3, terrain.getZoneJouee().size());
        //IA Panda a bien jouer le panda
        Assert.assertEquals(new Coordonnees(-1, 1, 0), panda.getCoordonnees());

    }

    //Parcelle & Jardinier
    @Test
    public void IAPandaTest3() throws TricheException {
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = new Terrain();
        LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();
        Jardinier jardinier = new Jardinier(terrain);
        Panda panda = new Panda(terrain);
        LaPiocheParcelle laPiocheParcelle = new LaPiocheParcelle();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("IAPanda");

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        feuilleJoueur.setMainObjectif(cartesObjectifs);

        Parcelle p2 = new Parcelle(new Coordonnees(-1, 1, 0));
        p2.setCouleur(Parcelle.Couleur.ROSE);
        terrain.changements(p2, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(-1, 1, 0)).mangerBambou();

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        laPiocheParcelle.getPioche().clear();
        Parcelle p3 = new Parcelle(new Coordonnees(1, 1, 0));
        p3.setCouleur(Parcelle.Couleur.JAUNE);
        laPiocheParcelle.getPioche().add(p3);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        terrain.changements(p3, IAPanda.getFeuilleJoueur());
                        return null;
                    }
                }).when(iService).poserParcelle(p3);
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(), 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(2, 2, 0, 0, 3, 3);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());

        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(new Coordonnees(-1, 1, 0), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(new Coordonnees(-1, 1, 0));
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
    public void IAPandaTest4() throws TricheException {
        Takenoko takenoko = new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("");

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        feuilleJoueur.setMainObjectif(cartesObjectifs);

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

        jardinier.deplacerEntite(p1.getCoord(), new FeuilleJoueur(""));
        panda.deplacerEntite(p1.getCoord(), new FeuilleJoueur(""));

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(), 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 2, 2, 2, 3, 3, 3, 3);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p3.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p3.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(p3.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(p3.getCoord());

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
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 2, 2, 2, 3, 3, 3, 3);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p2.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(p2.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(p2.getCoord());

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
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 2, 2, 2, 3, 3, 3, 3);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(new Coordonnees(0, 0, 0), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(new Coordonnees(0, 0, 0));
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(new Coordonnees(0, 0, 0), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(new Coordonnees(0, 0, 0));
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
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 2, 2, 2, 1, 1);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p2.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        terrain.verifObjectifAccompli(feuilleJoueur);
                        return null;
                    }
                }).when(iService).verifObjectifAccompli();
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 2);
                        return null;
                    }
                }).when(iService).piocherUnObjectif(2);
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        //IA Panda a bien jouer le panda
        Assert.assertEquals(p2.getCoord(), panda.getCoordonnees());

        Assert.assertEquals(5, IAPanda.getFeuilleJoueur().getPointsBot());
        //IA Panda a pioche une carte objectif
        Assert.assertEquals(TAILLE_MAIN_MAX, IAPanda.getMainObjectif().size());
    }

    //reussi un objectif une couleur de chaque quand le terrain est en place
    @Test
    public void IAPandaTest5() throws TricheException {
        Takenoko takenoko = new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("");

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);

        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        cartesObjectifs.add(new CarteObjectifPanda(6, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE));
        feuilleJoueur.setMainObjectif(cartesObjectifs);

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
        terrain.changements(p3, new FeuilleJoueur(""));
        terrain.getZoneJouee().get(new Coordonnees(0, 1, -1)).mangerBambou();

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));

        when(iService.jardinierGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 2, 2, 2, 3, 3, 3, 3);
        when(iService.feuilleJoueurGetNbBambouRose()).thenReturn(feuilleJoueur.getNbBambouRose());
        when(iService.feuilleJoueurGetNbBambouVert()).thenReturn(feuilleJoueur.getNbBambouVert());
        when(iService.feuilleJoueurGetNbBambouJaune()).thenReturn(feuilleJoueur.getNbBambouJaune());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p2.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p2.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(p2.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(p2.getCoord());

        when(iService.pandaGetCoordonnees()).thenReturn(panda.getCoordonnees());
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 3, 3, 3, 3, 3, 2, 2, 2);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        when(iService.feuilleJoueurGetNbBambouRose()).thenReturn(feuilleJoueur.getNbBambouRose());
        when(iService.feuilleJoueurGetNbBambouVert()).thenReturn(feuilleJoueur.getNbBambouVert());
        when(iService.feuilleJoueurGetNbBambouJaune()).thenReturn(feuilleJoueur.getNbBambouJaune());
        feuilleJoueur.initNbAction();
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p3.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p3.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(p3.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(p3.getCoord());
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.piocheParcelleIsEmpty()).thenReturn(laPiocheParcelle.getPioche().isEmpty());
        when(iService.pandaGetDeplacementsPossible()).thenReturn(panda.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.jardinierGetDeplacementsPossible()).thenReturn(jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 3, 3, 3, 3, 3, 2, 2, 2);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        when(iService.feuilleJoueurGetNbBambouRose()).thenReturn(feuilleJoueur.getNbBambouRose());
        when(iService.feuilleJoueurGetNbBambouVert()).thenReturn(feuilleJoueur.getNbBambouVert());
        when(iService.feuilleJoueurGetNbBambouJaune()).thenReturn(feuilleJoueur.getNbBambouJaune());
        feuilleJoueur.initNbAction();
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p1.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p1.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        jardinier.deplacerEntite(p1.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerJardinier(p1.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        terrain.verifObjectifAccompli(feuilleJoueur);
                        return null;
                    }
                }).when(iService).verifObjectifAccompli();
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //reussi un objectif une couleur de chaque
        Assert.assertEquals(6, feuilleJoueur.getPointsBot());
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
    public void IAPandaTest6() throws TricheException {
        Takenoko takenoko = new Takenoko();
        takenoko.initPartie();
        IAPanda IAPanda = new IAPanda();
        IAPanda.setNomBot("IA Panda");
        Terrain terrain = takenoko.getTerrain();
        LesPiochesObjectif lesPiochesObjectif = takenoko.getLesPiochesObjectif();
        Jardinier jardinier = takenoko.getJardinier();
        Panda panda = takenoko.getPanda();
        LaPiocheParcelle laPiocheParcelle = takenoko.getLaPiocheParcelle();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("");

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);


        ArrayList<CartesObjectifs> cartesObjectifs = new ArrayList<>();
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, 5));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.JAUNE, 4));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3));
        cartesObjectifs.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, 3));
        feuilleJoueur.setMainObjectif(cartesObjectifs);

        feuilleJoueur.incBambouJaune();

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
        when(iService.getFeuilleJoueur()).thenReturn(feuilleJoueur);
        when(iService.feuilleJoueurGetMainObjectif()).thenReturn(feuilleJoueur.getMainObjectif());
        when(iService.feuilleJoueurGetNbAction()).thenReturn(2, 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(0, 2, 2, 2, 3, 3, 3, 3);
        when(iService.feuilleJoueurGetNbBambouJaune()).thenReturn(feuilleJoueur.getNbBambouJaune());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        panda.deplacerEntite(p1.getCoord(), feuilleJoueur);
                        return null;
                    }
                }).when(iService).deplacerPanda(p1.getCoord());
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        terrain.verifObjectifAccompli(feuilleJoueur);
                        return null;
                    }
                }).when(iService).verifObjectifAccompli();
        //IA Panda va faire l'objectif JAUNE car il peut le finir sur ce tour
        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

        //reussi l'objectif JAUNE
        Assert.assertEquals(4, IAPanda.getFeuilleJoueur().getPointsBot());
    }
}

