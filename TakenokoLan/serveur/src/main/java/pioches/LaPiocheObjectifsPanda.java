package pioches;

import commun.ressources.CarteObjectifPanda;
import commun.ressources.CartesObjectifs;
import commun.ressources.Parcelle;

import java.util.ArrayList;
import java.util.Collections;

public class LaPiocheObjectifsPanda {

    private ArrayList<CartesObjectifs> pioche = new ArrayList<>();
    private static final int NB_OBJECTIF_JAUNE = 4;
    private static final int NB_OBJECTIF_ROSE = 3;
    private static final int NB_OBJECTIF_VERTE = 5;
    private static final int NB_OBJECTIF_TRIPLE = 3;
    private static final int SIX_POINTS = 6;
    private static final int CINQ_POINTS = 5;
    private static final int QUATRE_POINTS = 4;
    private static final int TROIS_POINTS = 3;

    public LaPiocheObjectifsPanda() {
        for (int i = 0; i < NB_OBJECTIF_JAUNE; i++) {
            pioche.add(new CarteObjectifPanda(Parcelle.Couleur.JAUNE, QUATRE_POINTS));
        }
        for (int i = 0; i < NB_OBJECTIF_ROSE; i++) {
            pioche.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, CINQ_POINTS));
        }
        for (int i = 0; i < NB_OBJECTIF_VERTE; i++) {
            pioche.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, TROIS_POINTS));
        }
        for (int i = 0; i < NB_OBJECTIF_TRIPLE; i++) {
            pioche.add(new CarteObjectifPanda(SIX_POINTS,Parcelle.Couleur.VERTE,Parcelle.Couleur.ROSE,Parcelle.Couleur.JAUNE));
        }

        //on double la pioche
        for (int i = 0; i < NB_OBJECTIF_JAUNE; i++) {
            pioche.add(new CarteObjectifPanda(Parcelle.Couleur.JAUNE, QUATRE_POINTS));
        }
        for (int i = 0; i < NB_OBJECTIF_ROSE; i++) {
            pioche.add(new CarteObjectifPanda(Parcelle.Couleur.ROSE, CINQ_POINTS));
        }
        for (int i = 0; i < NB_OBJECTIF_VERTE; i++) {
            pioche.add(new CarteObjectifPanda(Parcelle.Couleur.VERTE, TROIS_POINTS));
        }
        for (int i = 0; i < NB_OBJECTIF_TRIPLE; i++) {
            pioche.add(new CarteObjectifPanda(SIX_POINTS,Parcelle.Couleur.VERTE,Parcelle.Couleur.ROSE,Parcelle.Couleur.JAUNE));
        }
        Collections.shuffle(pioche);
    }

    public CartesObjectifs piocher() {
        CartesObjectifs objPar = pioche.get(0);
        pioche.remove(0);
        return objPar;
    }

    public ArrayList<CartesObjectifs> getPioche() {
        return pioche;
    }
}
