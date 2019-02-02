package takenoko.utilitaires;

import takenoko.ressources.CartesObjectifs;

import java.util.ArrayList;

public class MainJoueur {
    private CartesObjectifs cartesObjectifs;
    private int cout;
    private int nbBambouManquant;

    ArrayList<Coordonnees> coordonneesPandaPossible = new ArrayList<>();

    ArrayList<Coordonnees> coordonneesJardinierPossible = new ArrayList<>();

    public MainJoueur(CartesObjectifs cartesObjectifs, int cout) {
        this.cartesObjectifs = cartesObjectifs;
        this.cout = cout;
    }

    public ArrayList<Coordonnees> getCoordonneesPandaPossible() {
        return coordonneesPandaPossible;
    }

    public void setCoordonneesPandaPossible(ArrayList<Coordonnees> coordonneesPandaPossible) {
        this.coordonneesPandaPossible = coordonneesPandaPossible;
    }

    public CartesObjectifs getCartesObjectifs() {
        return cartesObjectifs;
    }

    public void setCartesObjectifs(CartesObjectifs cartesObjectifs) {
        this.cartesObjectifs = cartesObjectifs;
    }

    public int getCout() {
        return cout;
    }

    public void incCout() {
        cout++;
    }

    public ArrayList<Coordonnees> getCoordonneesJardinierPossible() {
        return coordonneesJardinierPossible;
    }

    public void setCoordonneesJardinierPossible(ArrayList<Coordonnees> coordonneesJardinierPossible) {
        this.coordonneesJardinierPossible = coordonneesJardinierPossible;
    }

    public int getNbBambouManquant() {
        return nbBambouManquant;
    }

    public void incNbBambouManquant() {
        nbBambouManquant++;
    }

    public void resetCout() {
        cout = 0;
    }

    public void resetNbBambouManquant() {
        nbBambouManquant = 0;
    }
}
