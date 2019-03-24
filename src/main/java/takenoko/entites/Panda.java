package takenoko.entites;

import takenoko.moteur.Terrain;
import takenoko.ressources.Coordonnees;
import takenoko.ressources.FeuilleJoueur;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Panda extends Entite {

    public Panda(Terrain terrain) {
        this.terrain = terrain;
        this.coordonnees = new Coordonnees(0, 0, 0);
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    @Override
    public void deplacerEntite(Coordonnees choixDeplacement, FeuilleJoueur feuilleJoueur) throws TricheException {
        if (feuilleJoueur.getPrecedant() != 2) {
            if (getDeplacementsPossible(terrain.getZoneJouee()).contains(choixDeplacement)) {
                feuilleJoueur.decNbACtion();
                feuilleJoueur.setPrecedant(2);
                this.coordonnees = choixDeplacement;
                mangerBambou(terrain.getZoneJouee().get(choixDeplacement), feuilleJoueur);
            } else {
                throw new TricheException("déplacement sur cette parcelle impossible");
            }
        } else {
            throw new TricheException("impossible de deplacer 2 fois panda");
        }
    }

    private void mangerBambou(Parcelle aManger, FeuilleJoueur feuilleJoueur) {
        if (aManger.getEffet() != Parcelle.Effet.ENCLOS && aManger.mangerBambou()) {
            if (aManger.getCouleur() == Parcelle.Couleur.VERTE) {
                feuilleJoueur.incBambouVert();
            } else if (aManger.getCouleur() == Parcelle.Couleur.JAUNE) {
                feuilleJoueur.incBambouJaune();
            } else if (aManger.getCouleur() == Parcelle.Couleur.ROSE) {
                feuilleJoueur.incBambouRose();
            }
        }
    }

    public ArrayList<Coordonnees> getDeplacementsPossible(LinkedHashMap<Coordonnees, Parcelle> zoneJoue) {
        ArrayList<Coordonnees> deplacementsPossible = new ArrayList<>();

        coordonnees.deplacementPossible(zoneJoue, deplacementsPossible);
        deplacementsPossible.remove(this.coordonnees);
        return deplacementsPossible;
    }


}
