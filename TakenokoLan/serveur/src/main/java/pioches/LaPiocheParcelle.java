package pioches;

import commun.ressources.Parcelle;

import java.util.ArrayList;
import java.util.Collections;

public class LaPiocheParcelle {
    /**
     * Créer la pioche de parcelles
     */
    private ArrayList<Parcelle> pioche = new ArrayList<>();
    public static final int NB_PARCELLE_ROSE_SANS_EFFET = 4;

    public static final int NB_PARCELLE_VERTE_SANS_EFFET = 6;
    public static final int NB_PARCELLE_VERTE_EFFET_PUIT = 2;
    public static final int NB_PARCELLE_VERTE_EFFET_ENCLOS = 2;

    public static final int NB_PARCELLE_JAUNE_SANS_EFFET = 6;

    public static final int NB_CARTE_A_PIOCHER = 3;

    public LaPiocheParcelle() {

        for (int i = 0; i < NB_PARCELLE_ROSE_SANS_EFFET; i++) {
            pioche.add(new Parcelle(Parcelle.Couleur.ROSE, Parcelle.Effet.AUCUN));
        }
        pioche.add(new Parcelle(Parcelle.Couleur.ROSE, Parcelle.Effet.PUIT));
        pioche.add(new Parcelle(Parcelle.Couleur.ROSE, Parcelle.Effet.ENCLOS));
        pioche.add(new Parcelle(Parcelle.Couleur.ROSE, Parcelle.Effet.ENGRAIS));

        for (int i = 0; i < NB_PARCELLE_VERTE_SANS_EFFET; i++) {
            pioche.add(new Parcelle(Parcelle.Couleur.VERTE, Parcelle.Effet.AUCUN));
        }
        for (int i = 0; i < NB_PARCELLE_VERTE_EFFET_PUIT; i++) {
            pioche.add(new Parcelle(Parcelle.Couleur.VERTE, Parcelle.Effet.PUIT));
        }
        for (int i = 0; i < NB_PARCELLE_VERTE_EFFET_ENCLOS; i++) {
            pioche.add(new Parcelle(Parcelle.Couleur.VERTE, Parcelle.Effet.ENCLOS));
        }
        pioche.add(new Parcelle(Parcelle.Couleur.VERTE, Parcelle.Effet.ENGRAIS));

        for (int i = 0; i < NB_PARCELLE_JAUNE_SANS_EFFET; i++) {
            pioche.add(new Parcelle(Parcelle.Couleur.JAUNE, Parcelle.Effet.AUCUN));
        }
        pioche.add(new Parcelle(Parcelle.Couleur.JAUNE, Parcelle.Effet.PUIT));
        pioche.add(new Parcelle(Parcelle.Couleur.JAUNE, Parcelle.Effet.ENCLOS));
        pioche.add(new Parcelle(Parcelle.Couleur.JAUNE, Parcelle.Effet.ENGRAIS));

        Collections.shuffle(pioche);
    }

    public ArrayList<Parcelle> getPioche() {
        return pioche;
    }

    /**
     * on pioche une parcelle
     */

    public ArrayList<Parcelle> piocherParcelle() {
        ArrayList<Parcelle> main = new ArrayList<>();
        if (pioche.size() > NB_CARTE_A_PIOCHER) {
            for (int i = 0; i < NB_CARTE_A_PIOCHER; i++) {
                main.add(pioche.get(0));
                pioche.remove(0);
            }
        } else {
            int size = pioche.size();
            for (int i = 0; i < size; i++) {
                main.add(pioche.get(0));
                pioche.remove(0);
            }
        }
        return main;
    }

    public void reposeSousLaPioche(ArrayList<Parcelle> aRemettre) {
        pioche.addAll(aRemettre);
    }
}

