package takenoko.pioches;

import takenoko.ressources.CarteObjectifParcelle;
import takenoko.ressources.CartesObjectifs;
import takenoko.ressources.Parcelle;

import java.util.ArrayList;
import java.util.Collections;

public class LaPiocheObjectifsParcelles {

    private ArrayList<CartesObjectifs> pioche = new ArrayList<>();

    public LaPiocheObjectifsParcelles() {
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.TRIANGLE, Parcelle.Couleur.VERTE, 2));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.TRIANGLE, Parcelle.Couleur.JAUNE, 3));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.TRIANGLE, Parcelle.Couleur.ROSE, 4));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LIGNE, Parcelle.Couleur.VERTE, 2));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LIGNE, Parcelle.Couleur.JAUNE, 3));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LIGNE, Parcelle.Couleur.ROSE, 4));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.COURBE, Parcelle.Couleur.VERTE, 2));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.COURBE, Parcelle.Couleur.JAUNE, 3));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.COURBE, Parcelle.Couleur.ROSE, 4));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.VERTE, 3));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.JAUNE, 4));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.ROSE, 5));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.VERTE, Parcelle.Couleur.JAUNE, 3));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.VERTE, Parcelle.Couleur.ROSE, 4));
        pioche.add(new CarteObjectifParcelle(CarteObjectifParcelle.Motif.LOSANGE, Parcelle.Couleur.ROSE, Parcelle.Couleur.JAUNE, 5));

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
