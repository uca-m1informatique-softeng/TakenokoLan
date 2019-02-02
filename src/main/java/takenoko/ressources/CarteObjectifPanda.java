package takenoko.ressources;

import takenoko.ia.IA;
import takenoko.moteur.Terrain;

public class CarteObjectifPanda extends CartesObjectifs {

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
    public void verifObjectif(Terrain terrain, Parcelle valeur, IA bot) {
        switch (super.getPoints()) {
            case 3:
                doubleBambou(Parcelle.Couleur.VERTE, bot);
                break;
            case 4:
                doubleBambou(Parcelle.Couleur.JAUNE, bot);
                break;
            case 5:
                doubleBambou(Parcelle.Couleur.ROSE, bot);
                break;
            case 6:
                tripleBambou(bot);
                break;
        }
    }

    private void doubleBambou(Parcelle.Couleur couleur, IA bot) {
        if (Parcelle.Couleur.JAUNE == couleur && bot.getFeuilleJoueur().getNb_bambou_jaune() >= 2) {
            bot.getFeuilleJoueur().decBambouJaune();
            bot.getFeuilleJoueur().decBambouJaune();
            bot.getFeuilleJoueur().majPointsEtMain(this, bot);
        } else if (Parcelle.Couleur.ROSE == couleur && bot.getFeuilleJoueur().getNbBambouRose() >= 2) {
            bot.getFeuilleJoueur().decBambouRose();
            bot.getFeuilleJoueur().decBambouRose();
            bot.getFeuilleJoueur().majPointsEtMain(this, bot);
        } else if (Parcelle.Couleur.VERTE == couleur && bot.getFeuilleJoueur().getNbBambouVert() >= 2) {
            bot.getFeuilleJoueur().decBambouVert();
            bot.getFeuilleJoueur().decBambouVert();
            bot.getFeuilleJoueur().majPointsEtMain(this, bot);
        }
    }

    private void tripleBambou(IA bot) {
        if (bot.getFeuilleJoueur().getNbBambouRose() >= 1 && bot.getFeuilleJoueur().getNb_bambou_jaune() >= 1 && bot.getFeuilleJoueur().getNbBambouVert() >= 1) {
            bot.getFeuilleJoueur().decBambouVert();
            bot.getFeuilleJoueur().decBambouRose();
            bot.getFeuilleJoueur().decBambouJaune();
            bot.getFeuilleJoueur().majPointsEtMain(this, bot);
        }
    }
}
