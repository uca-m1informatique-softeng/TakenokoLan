package joueur.service;

import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.FeuilleJoueur;
import commun.ressources.Parcelle;
import commun.triche.TricheException;

import java.util.ArrayList;

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

    int[] connect();

    ArrayList<Parcelle> getZoneJouee();

    ArrayList<Coordonnees> getListeZonesPosables();

}
