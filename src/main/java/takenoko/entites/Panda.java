package takenoko.entites;

import takenoko.ia.IA;
import takenoko.moteur.Terrain;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.HashMap;

public class Panda extends Entite {

    public Panda(Terrain terrain) {
        this.terrain = terrain;
        this.coordonnees = new Coordonnees(0, 0, 0);
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    @Override
    public void deplacerEntite(Coordonnees choixDeplacement, IA bot) throws TricheException {
        if (bot.getFeuilleJoueur().getPrecedant() != 2) {
            if (getDeplacementsPossible(terrain.getZoneJouee()).contains(choixDeplacement)) {
                bot.getFeuilleJoueur().decNbACtion();
                bot.getFeuilleJoueur().setPrecedant(2);
                this.coordonnees = choixDeplacement;
                mangerBambou(terrain.getZoneJouee().get(choixDeplacement), bot);
            } else {
                throw new TricheException("déplacement sur cette parcelle impossible");
            }
        } else {
            throw new TricheException("impossible de deplacer 2 fois panda");
        }
    }

    private void mangerBambou(Parcelle aManger, IA bot) {
        if (aManger.getEffet() != Parcelle.Effet.ENCLOS && aManger.mangerBambou()) {
            if (aManger.getCouleur() == Parcelle.Couleur.VERTE) {
                bot.getFeuilleJoueur().incBambouVert();
            } else if (aManger.getCouleur() == Parcelle.Couleur.JAUNE) {
                bot.getFeuilleJoueur().incBambouJaune();
            } else if (aManger.getCouleur() == Parcelle.Couleur.ROSE) {
                bot.getFeuilleJoueur().incBambouRose();
            }
        }
    }

    public ArrayList<Coordonnees> getDeplacementsPossible(HashMap<Coordonnees, Parcelle> zoneJoue) {
        ArrayList<Coordonnees> deplacementsPossible = new ArrayList<>();

        coordonnees.deplacementPossible(zoneJoue, deplacementsPossible);
        deplacementsPossible.remove(this.coordonnees);
        return deplacementsPossible;
    }

}
