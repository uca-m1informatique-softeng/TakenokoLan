package serveur.utilitaires;

import commun.ressources.FeuilleJoueur;

public class StatistiqueJoueur {
    int refJoueur;
    private int nbVictoire;
    private int nbToursTotal;
    private int nbPointsTotal;
    private int nbToursMIN = 0;
    private int nbToursMAX = 0;


    private FeuilleJoueur feuilleJoueur;

    public StatistiqueJoueur(int refJoueur, int nbVictoire, int nbPointsTotal, int nbToursTotal, String name) {
        this.refJoueur = refJoueur;
        this.nbVictoire = nbVictoire;
        this.nbPointsTotal = nbPointsTotal;
        this.nbToursTotal = nbToursTotal;
        feuilleJoueur = new FeuilleJoueur(name);
    }

    public int getNbVictoire() {
        return nbVictoire;
    }

    public int getNbPointsTotal() {
        return nbPointsTotal;
    }

    public void incrNVictoire() {
        nbVictoire++;

    }

    public void incrNbPointsTotal(int nbPoints) {
        nbPointsTotal = nbPointsTotal + nbPoints;
    }

    public void incrNbToursTotal(int nbTours) {
        if (nbToursMIN == 0 || nbToursMIN > nbTours) {
            nbToursMIN = nbTours;
        }
        if (nbToursMAX == 0 || nbToursMAX < nbTours) {
            nbToursMAX = nbTours;
        }
        nbToursTotal = nbToursTotal + nbTours;
    }

    public int getNbToursTotal() {
        return nbToursTotal;
    }

    public int getNbToursMIN() {
        return nbToursMIN;
    }

    public int getNbToursMAX() {
        return nbToursMAX;
    }

    public FeuilleJoueur getFeuilleJoueur() {
        return feuilleJoueur;
    }

    public void setFeuilleJoueur(FeuilleJoueur feuilleJoueur) {
        this.feuilleJoueur = feuilleJoueur;
    }

    public int getRefJoueur() {
        return refJoueur;
    }

    public void setRefJoueur(int refJoueur) {
        this.refJoueur = refJoueur;
    }
}
