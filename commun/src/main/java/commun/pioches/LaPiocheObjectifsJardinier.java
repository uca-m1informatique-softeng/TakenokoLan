package commun.pioches;

import commun.ressources.CarteObjectifJardinier;
import commun.ressources.CartesObjectifs;
import commun.ressources.Parcelle;

import java.util.ArrayList;
import java.util.Collections;

public class LaPiocheObjectifsJardinier {
    private ArrayList<CartesObjectifs> pioche = new ArrayList<>();
    private static final int GRAND_BAMBOU = 3;
    private static final int MOYEN_BAMBOU = 2;
    private static final int SIX_POINTS = 6;
    private static final int CINQ_POINTS = 5;
    private static final int SEPT_POINTS = 7;
    private static final int QUATRE_POINTS = 4;
    private static final int TROIS_POINTS = 3;
    private static final int HUIT_POINTS = 8;

    public LaPiocheObjectifsJardinier() {
        //sans effet
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.VERTE, CINQ_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.AUCUN));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.ROSE, SEPT_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.AUCUN));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.JAUNE, SIX_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.AUCUN));

        //avec effet puit
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.VERTE, QUATRE_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.PUIT));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.ROSE, SIX_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.PUIT));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.JAUNE, CINQ_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.PUIT));

        //effet enclos
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.VERTE, QUATRE_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.ENCLOS));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.ROSE, SIX_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.ENCLOS));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.JAUNE, CINQ_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.ENCLOS));

        //effet engrais
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.VERTE, TROIS_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.ENGRAIS));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.ROSE, CINQ_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.ENGRAIS));
        pioche.add(new CarteObjectifJardinier(GRAND_BAMBOU, Parcelle.Couleur.JAUNE, QUATRE_POINTS, CartesObjectifs.Motif.POINT, Parcelle.Effet.ENGRAIS));

        //motif double
        pioche.add(new CarteObjectifJardinier(MOYEN_BAMBOU, Parcelle.Couleur.ROSE, SIX_POINTS, CartesObjectifs.Motif.LIGNE));

        //motif quatre
        pioche.add(new CarteObjectifJardinier(MOYEN_BAMBOU, Parcelle.Couleur.VERTE, HUIT_POINTS, CartesObjectifs.Motif.LOSANGE));

        //motif triple
        pioche.add(new CarteObjectifJardinier(MOYEN_BAMBOU, Parcelle.Couleur.JAUNE, SEPT_POINTS, CartesObjectifs.Motif.TRIANGLE));

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
