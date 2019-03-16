package takenoko.service;

import takenoko.ressources.CartesObjectifs;
import takenoko.ressources.Coordonnees;
import takenoko.ressources.FeuilleJoueur;
import takenoko.moteur.Terrain;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface IClientService {
    ArrayList<Parcelle> piocher();

    void reposeSousLaPioche(ArrayList<Parcelle> aRemettre);

    Boolean piocheParcelleIsEmpty();

    ArrayList<Coordonnees> pandaGetDeplacementsPossible();

    Boolean piochePandaIsEmpty();

    ArrayList<Coordonnees> jardinierGetDeplacementsPossible();

    FeuilleJoueur getFeuilleJoueur();

    void feuilleJoueurInitNbAction();

    int feuilleJoueurGetNbAction();

    int feuilleJoueurGetActionChoisie();

    void feuilleJoueurSetActionChoisie(int actionChoisie);

    int feuilleJoueurGetNbBambouRose();

    int feuilleJoueurGetNbBambouVert();

    int feuilleJoueurGetNbBambouJaune();

    void feuilleJoueurDecNbACtion();

    void deplacerPanda(Coordonnees coordonnees) throws TricheException;

    void deplacerJardinier(Coordonnees coordonnees) throws TricheException;

    void poserParcelle(Parcelle parcelle) throws TricheException;

    void piocherUnObjectif(int i) throws TricheException;

    ArrayList<CartesObjectifs> feuilleJoueurGetMainObjectif();

    void verifObjectifAccompli();

    Coordonnees pandaGetCoordonnees();

    Coordonnees jardinierGetCoordonnees();

    LinkedHashMap<Coordonnees,Parcelle> getZoneJouee();
}
