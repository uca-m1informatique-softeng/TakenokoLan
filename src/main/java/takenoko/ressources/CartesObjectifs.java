package takenoko.ressources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import takenoko.moteur.Terrain;

@JsonDeserialize(as = CarteObjectifPanda.class)
public abstract class CartesObjectifs {
    public enum Motif {LIGNE, COURBE, TRIANGLE, LOSANGE, POINT}

    private Parcelle.Couleur couleur;
    private Parcelle.Couleur couleur2;
    private Parcelle.Couleur couleur3;
    private int points;
    private Motif motif;
    private int bambou;
    private Parcelle.Effet effet;

    public CartesObjectifs(Parcelle.Couleur couleur, int points, Motif motif, int bambou, Parcelle.Effet effet) {
        this.couleur = couleur;
        this.points = points;
        this.motif = motif;
        this.bambou = bambou;
        this.effet = effet;
    }

    public CartesObjectifs(Parcelle.Couleur couleur, int points, Motif motif, int bambou) {
        this.couleur = couleur;
        this.points = points;
        this.motif = motif;
        this.bambou = bambou;
    }

    public CartesObjectifs(Parcelle.Couleur couleur, int points, Motif motif) {
        this.couleur = couleur;
        this.points = points;
        this.motif = motif;
    }

    public CartesObjectifs(Parcelle.Couleur couleur, Parcelle.Couleur couleur2, int points, Motif motif) {
        this.couleur = couleur;
        this.couleur2 = couleur2;
        this.points = points;
        this.motif = motif;
    }

    public CartesObjectifs(Parcelle.Couleur couleur, int points) {
        this.points = points;
        this.couleur = couleur;
    }

    public CartesObjectifs(int points, Parcelle.Couleur couleur, Parcelle.Couleur couleur2, Parcelle.Couleur couleur3) {
        this.points = points;
        this.couleur = couleur;
        this.couleur2 = couleur2;
        this.couleur3 = couleur3;

    }
    public CartesObjectifs(){}

    @Override
    public String toString() {
        return "CartesObjectifs{" +
                "couleur=" + couleur +
                ", couleur2=" + couleur2 +
                ", couleur3=" + couleur3 +
                ", points=" + points +
                ", motif=" + motif +
                ", bambou=" + bambou +
                ", effet=" + effet +
                '}';
    }

    public int getPoints() {
        return points;
    }

    public Parcelle.Couleur getCouleur2() {
        return couleur2;
    }

    public Parcelle.Couleur getCouleur() {
        return couleur;
    }

    public Parcelle.Couleur getCouleur3() {
        return couleur3;
    }

    public Motif getMotif() {
        return motif;
    }

    public int getBambou() {
        return bambou;
    }

    public Parcelle.Effet getEffet() {
        return effet;
    }

    public abstract void verifObjectif(Terrain terrain, Parcelle valeur, FeuilleJoueur feuilleJoueur);
}