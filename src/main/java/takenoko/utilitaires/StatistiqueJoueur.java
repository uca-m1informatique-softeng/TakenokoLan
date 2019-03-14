package takenoko.utilitaires;

import org.springframework.beans.factory.annotation.Autowired;
import takenoko.ia.IA;
import takenoko.ressources.FeuilleJoueur;

public class StatistiqueJoueur {
    private IA ia;
    private int nbVictoire;
    private int nbToursTotal;
    private int nbPointsTotal;
    private int nbToursMIN = 0;
    private int nbToursMAX = 0;


    private FeuilleJoueur feuilleJoueur;
    public StatistiqueJoueur(IA ia, int nbVictoire, int nbPointsTotal, int nbToursTotal,String name) {
        this.ia = ia;
        this.nbVictoire = nbVictoire;
        this.nbPointsTotal = nbPointsTotal;
        this.nbToursTotal = nbToursTotal;
        feuilleJoueur=new FeuilleJoueur(name);
    }

    public IA getIa() {
        return ia;
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

    public void setIa(IA ia) {
        this.ia = ia;
    }

    public void incrNbToursTotal(int nbTours) {
        if(nbToursMIN==0 || nbToursMIN > nbTours){
            nbToursMIN=nbTours;
        }
        if(nbToursMAX==0 || nbToursMAX < nbTours){
            nbToursMAX=nbTours;
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
}
