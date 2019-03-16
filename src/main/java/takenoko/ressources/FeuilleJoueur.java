package takenoko.ressources;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeuilleJoueur {
    public static final Logger LOGGER = Logger.getLogger(FeuilleJoueur.class.getCanonicalName());

    private ArrayList<CartesObjectifs> mainObjectif = new ArrayList<>();
    private String name;
    private int nbBambouJaune = 0;
    private int nbBambouRose = 0;
    private int nbBambouVert = 0;

    private int nb_irrigation = 0;

    private int nbAction = 2;
    private int actionChoisie = 5; //pour sortir de la possibilité d'actions (4 max) et ne pas fausser l'action aléatoire
    private int precedant = 15;

    private int pointsBot = 0;
    private int nbObjectifsValide = 0;

    public FeuilleJoueur(String name) {
        this.name = name;
        LOGGER.setLevel(Level.OFF);
    }

    public void decNbACtion() {
        nbAction--;
    }

    public void initNbAction() {
        nbAction = 2;
        actionChoisie = 5;
        precedant = 15;
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

    public void majPointsEtMain(CartesObjectifs carte) {
        pointsBot = pointsBot + carte.getPoints();
        LOGGER.info(name + " fini l'objectif de : " + carte + ".");
        mainObjectif.remove(carte);
        nbObjectifsValide++;
        LOGGER.info("nombre d'objectif validé : " + nbObjectifsValide);
    }

    public int getNbObjectifsValide() {
        return nbObjectifsValide;
    }

    public int getNbBambouJaune() {
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

    @Override
    public String toString() {
        return "FeuilleJoueur{" +
                "nbBambouJaune=" + nbBambouJaune +
                ", nbBambouRose=" + nbBambouRose +
                ", nbBambouVert=" + nbBambouVert +
                ", nb_irrigation=" + nb_irrigation +
                ", nbAction=" + nbAction +
                ", actionChoisie=" + actionChoisie +
                ", precedant=" + precedant +
                ", pointsBot=" + pointsBot +
                ", nbObjectifsValide=" + nbObjectifsValide +
                '}';
    }

    public ArrayList<CartesObjectifs> getMainObjectif() {
        return mainObjectif;
    }

    public String getName() {
        return name;
    }

    public void setMainObjectif(ArrayList<CartesObjectifs> mainObjectif) {
        this.mainObjectif = mainObjectif;
    }
}
