package takenoko.entites;

import takenoko.ia.IA;
import takenoko.moteur.Terrain;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Jardinier extends Entite {

    public Jardinier(Terrain terrain) {
        this.terrain = terrain;
        this.coordonnees = new Coordonnees(0, 0, 0);
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    @Override
    public void deplacerEntite(Coordonnees coordonnees, IA bot)throws TricheException {
        if(bot.getFeuilleJoueur().getPrecedant()!=3){
            if (getDeplacementsPossible(terrain.getZoneJouee()).contains(coordonnees)) {
                bot.getFeuilleJoueur().decNbACtion();
                bot.getFeuilleJoueur().setPrecedant(3);
                this.coordonnees = coordonnees;
                pousserBambou();
            }else{
                throw new TricheException("déplacement sur cette parcelle impossible");
            }
        }else{
            throw new TricheException("impossible de deplacer 2 fois jardinier");
        }
    }

    public ArrayList<Coordonnees> getDeplacementsPossible(LinkedHashMap<Coordonnees, Parcelle> zoneJoue) {
        ArrayList<Coordonnees> deplacementsPossible = new ArrayList<>();
        coordonnees.deplacementPossible(zoneJoue, deplacementsPossible);
        deplacementsPossible.remove(this.coordonnees);
        return deplacementsPossible;
    }

    public void pousserBambou() {
        for (Map.Entry<Coordonnees, Parcelle> entry : terrain.getZoneJouee().entrySet()) {
            Coordonnees cle = entry.getKey();
            Parcelle valeur = entry.getValue();
            if (terrain.getZoneJouee().get(coordonnees).getCouleur() == valeur.getCouleur() && terrain.getZoneJouee().get(coordonnees).isIrriguee() && (
                    (cle.equals(terrain.getHautDroite(coordonnees)) && terrain.getZoneJouee().get(terrain.getHautDroite(coordonnees)).isIrriguee()) ||
                            (cle.equals(terrain.getGauche(coordonnees)) && terrain.getZoneJouee().get(terrain.getGauche(coordonnees)).isIrriguee()) ||
                            (cle.equals(terrain.getBasDroite(coordonnees)) && terrain.getZoneJouee().get(terrain.getBasDroite(coordonnees)).isIrriguee()) ||
                            (cle.equals(terrain.getBasGauche(coordonnees)) && terrain.getZoneJouee().get(terrain.getBasGauche(coordonnees)).isIrriguee()) ||
                            (cle.equals(terrain.getDroite(coordonnees)) && terrain.getZoneJouee().get(terrain.getDroite(coordonnees)).isIrriguee()) ||
                            (cle.equals(terrain.getHautGauche(coordonnees)) && terrain.getZoneJouee().get(terrain.getHautGauche(coordonnees)).isIrriguee()))) {
                if (valeur.getEffet() == Parcelle.Effet.PUIT) {
                    valeur.pousserBambou();
                    valeur.pousserBambou();
                } else {
                    valeur.pousserBambou();
                }
            }
        }
        terrain.getZoneJouee().get(coordonnees).pousserBambou();
    }
}

