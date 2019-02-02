package takenoko.ressources;

import takenoko.ia.IA;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FeuilleJoueur {
    public static final Logger LOGGER = Logger.getLogger(FeuilleJoueur.class.getCanonicalName());
    private int nbBambouJaune = 0;
    private int nbBambouRose = 0;
    private int nbBambouVert = 0;

    private int nb_irrigation = 0;

    private int nbAction = 2;
    private int actionChoisie = 5; //pour sortir de la possibilité d'actions (4 max) et ne pas fausser l'action aléatoire
    private int precedant = 15;

    private int pointsBot = 0;
    private int nbObjectifsValide = 0;

    public FeuilleJoueur() {
        LOGGER.setLevel(Level.OFF);
    }

    public void decNbACtion() {
        nbAction--;
    }

    public void initNbAction() {
        nbAction = 2;
        actionChoisie = 5;
        precedant=15;
    }

    public void decBambouJaune() {
        nbBambouJaune--;
    }

    public void decBambouVert() {
        nbBambouVert--;
    }

    public void decBambouRose() {
        nbBambouRose--;
    }

    public void incBambouJaune() {
        nbBambouJaune++;
    }

    public void incBambouVert() {
        nbBambouVert++;
    }

    public void incBambouRose() {
        nbBambouRose++;
    }


    public void incIrrigation() {
        decNbACtion();
        nb_irrigation++;
    }

    public void decIrrigation() {
        nb_irrigation--;
    }

    public int getNb_irrigation() {
        return nb_irrigation;
    }

    public void majPointsEtMain(CartesObjectifs carte, IA bot) {
        pointsBot = pointsBot + carte.getPoints();
        LOGGER.info(bot.getNomBot() + " fini l'objectif de : " + carte + ".");
        bot.getMainObjectif().remove(carte);
        nbObjectifsValide++;
        LOGGER.info("nombre d'objectif validé : " + nbObjectifsValide);
    }

    public int getNbObjectifsValide() {
        return nbObjectifsValide;
    }

    public int getNb_bambou_jaune() {
        return nbBambouJaune;
    }

    public int getNbBambouRose() {
        return nbBambouRose;
    }

    public int getNbBambouVert() {
        return nbBambouVert;
    }

    public int getNbAction() {
        return nbAction;
    }

    public int getActionChoisie() {
        return actionChoisie;
    }

    public int getPointsBot() {
        return pointsBot;
    }

    public void setActionChoisie(int actionChoisie) {
        this.actionChoisie = actionChoisie;
    }

    public void setPointsBot(int pointsBot) {
        this.pointsBot = pointsBot;
    }

    public int getPrecedant() {
        return precedant;
    }

    public void setPrecedant(int precedant) {
        this.precedant = precedant;
    }
}
