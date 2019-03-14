package takenoko.ressources;

import takenoko.ia.IA;
import takenoko.moteur.Terrain;

public class CarteObjectifPanda extends CartesObjectifs {

    public CarteObjectifPanda(){
        super();
    }

    public CarteObjectifPanda(Parcelle.Couleur couleur, int points) {
        super(couleur, points);
    }

    public CarteObjectifPanda(int points, Parcelle.Couleur couleur, Parcelle.Couleur couleur2, Parcelle.Couleur couleur3) {
        super(points,couleur,couleur2,couleur3);
    }

    public String toString() {
        if (getPoints() == 6) {
            return "Panda Triple couleur pour " + super.getPoints() + " points";
        } else {
            return "Panda de couleur " + super.getCouleur() + " pour " + super.getPoints() + " points";
        }
    }

    @Override
    public void verifObjectif(Terrain terrain, Parcelle valeur, FeuilleJoueur feuilleJoueur) {
        switch (super.getPoints()) {
            case 3:
                doubleBambou(Parcelle.Couleur.VERTE, feuilleJoueur);
                break;
            case 4:
                doubleBambou(Parcelle.Couleur.JAUNE, feuilleJoueur);
                break;
            case 5:
                doubleBambou(Parcelle.Couleur.ROSE, feuilleJoueur);
                break;
            case 6:
                tripleBambou(feuilleJoueur);
                break;
        }
    }

    private void doubleBambou(Parcelle.Couleur couleur, FeuilleJoueur feuilleJoueur) {
        if (Parcelle.Couleur.JAUNE == couleur &&feuilleJoueur.getNbBambouJaune() >= 2) {
            feuilleJoueur.decBambouJaune();
            feuilleJoueur.decBambouJaune();
            feuilleJoueur.majPointsEtMain(this);
        } else if (Parcelle.Couleur.ROSE == couleur && feuilleJoueur.getNbBambouRose() >= 2) {
            feuilleJoueur.decBambouRose();
            feuilleJoueur.decBambouRose();
            feuilleJoueur.majPointsEtMain(this);
        } else if (Parcelle.Couleur.VERTE == couleur && feuilleJoueur.getNbBambouVert() >= 2) {
            feuilleJoueur.decBambouVert();
            feuilleJoueur.decBambouVert();
            feuilleJoueur.majPointsEtMain(this);
        }
    }

    private void tripleBambou(FeuilleJoueur feuilleJoueur) {
        if (feuilleJoueur.getNbBambouRose() >= 1 && feuilleJoueur.getNbBambouJaune() >= 1 && feuilleJoueur.getNbBambouVert() >= 1) {
            feuilleJoueur.decBambouVert();
            feuilleJoueur.decBambouRose();
            feuilleJoueur.decBambouJaune();
            feuilleJoueur.majPointsEtMain(this);
        }
    }
}
