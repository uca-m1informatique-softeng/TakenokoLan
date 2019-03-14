package takenoko.moteur;

import takenoko.ia.IA;
import takenoko.ressources.*;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Terrain {

    private LinkedHashMap<Coordonnees, Parcelle> zoneJouee;
    private HashMap<Coordonnees, Integer> zoneDispo;
    private ArrayList<Coordonnees> listeZonesPosables;
    private ArrayList<Coordonnees> listeAdjacentsSource;
    private ArrayList<Irrigation> listeIrrigationsDispo;
    private ArrayList<Irrigation> listeIrrigationsPosables;
    private ArrayList<Irrigation> listeIrrigationsJouees;
    public static final Logger LOGGER = Logger.getLogger(Terrain.class.getCanonicalName());

    public Terrain() {
        LOGGER.setLevel(Level.OFF);
        // on crée la zone de jeu ( terrain sur lequel on posera les parcelles choisies par le bot)
        zoneJouee = new LinkedHashMap<>();
        zoneDispo = new HashMap<>();
        listeZonesPosables = new ArrayList<>();
        listeAdjacentsSource = new ArrayList<>();
        listeIrrigationsDispo = new ArrayList<>();
        listeIrrigationsPosables = new ArrayList<>();
        listeIrrigationsJouees = new ArrayList<>();

        // création de la source
        Parcelle source = new Parcelle(new Coordonnees(0, 0, 0));
        source.setCouleur(Parcelle.Couleur.SOURCE);
        listeAdjacentsSource = getAdjacents(source.getCoord());
        zoneJouee.put(source.getCoord(), source);
        LOGGER.info("La source est posée en " + source + ".");

        // génération de la liste de parcelles libres au début de la partie (celles autour de la source)
        LOGGER.info("Liste des parcelles libres au début de la partie :");
        zoneDispo.put(getGauche(source.getCoord()), 2);
        zoneDispo.put(getHautGauche(source.getCoord()), 2);
        zoneDispo.put(getHautDroite(source.getCoord()), 2);
        zoneDispo.put(getDroite(source.getCoord()), 2);
        zoneDispo.put(getBasDroite(source.getCoord()), 2);
        zoneDispo.put(getBasGauche(source.getCoord()), 2);
        for (Map.Entry<Coordonnees, Integer> entry : zoneDispo.entrySet()) {
            Coordonnees cle = entry.getKey();
            LOGGER.info("" + cle);
        }

        // màj listeZonesPosables au début de la partie
        for (Map.Entry<Coordonnees, Integer> entry : zoneDispo.entrySet()) {
            Coordonnees cle = entry.getKey();
            listeZonesPosables.add(cle);
        }
    }

    public ArrayList<Coordonnees> getAdjacents(Coordonnees coord) {
        ArrayList<Coordonnees> listeAdjacences = new ArrayList<>();
        listeAdjacences.add(getGauche(coord));
        listeAdjacences.add(getHautGauche(coord));
        listeAdjacences.add(getHautDroite(coord));
        listeAdjacences.add(getDroite(coord));
        listeAdjacences.add(getBasDroite(coord));
        listeAdjacences.add(getBasGauche(coord));
        return listeAdjacences;
    }

    // à mettre dans la classe Coordonees
    public Coordonnees getGauche(Coordonnees coord) {
        return new Coordonnees(coord.getX() - 1, coord.getY() + 1, coord.getZ());
    }

    public Coordonnees getHautGauche(Coordonnees coord) {
        return new Coordonnees(coord.getX(), coord.getY() + 1, coord.getZ() - 1);
    }

    public Coordonnees getHautDroite(Coordonnees coord) {
        return new Coordonnees(coord.getX() + 1, coord.getY(), coord.getZ() - 1);
    }

    public Coordonnees getDroite(Coordonnees coord) {
        return new Coordonnees(coord.getX() + 1, coord.getY() - 1, coord.getZ());
    }

    public Coordonnees getBasDroite(Coordonnees coord) {
        return new Coordonnees(coord.getX(), coord.getY() - 1, coord.getZ() + 1);
    }

    public Coordonnees getBasGauche(Coordonnees coord) {
        return new Coordonnees(coord.getX() - 1, coord.getY(), coord.getZ() + 1);
    }

    public void majZoneDispo(Parcelle parcelle) {
        ArrayList<Coordonnees> listeAdjacences = getAdjacents(parcelle.getCoord());
        for (int i = 0; i < listeAdjacences.size(); i++) {
            if (zoneDispo.containsKey(listeAdjacences.get(i))) {
                incrementer(listeAdjacences.get(i));
            } else {
                zoneDispo.put(listeAdjacences.get(i), 1);
            }
        }
    }

    public void incrementer(Coordonnees coord) {
        for (Map.Entry<Coordonnees, Integer> entry : zoneDispo.entrySet()) {
            Coordonnees cle = entry.getKey();
            Integer valeur = entry.getValue();
            if (cle.equals(coord)) {
                zoneDispo.put(cle, valeur + 1);
            }
        }
    }

    public void supprimer(Coordonnees coord) {
        for (int i = 0; i < listeZonesPosables.size(); i++) {
            if (listeZonesPosables.get(i).equals(coord)) {
                listeZonesPosables.remove(i);
            }
        }
    }

    public LinkedHashMap<Coordonnees, Parcelle> getZoneJouee() {
        return zoneJouee;
    }

    public HashMap<Coordonnees, Integer> getZoneDispo() {
        return zoneDispo;
    }

    public ArrayList<Coordonnees> getListeZonesPosables() {
        return listeZonesPosables;
    }

    public void changements(Parcelle coupJoue, FeuilleJoueur feuilleJoueur) throws TricheException{
        if (feuilleJoueur.getPrecedant() != 0) {
            if (zoneDispo.containsKey(coupJoue.getCoord())) {
                feuilleJoueur.decNbACtion();
                feuilleJoueur.setPrecedant(0);
                // si le coup joué est autour de la source, alors la parcelle est irriguée (et un bambou pousse)
                for (int i = 0; i < listeAdjacentsSource.size(); i++) {
                    if (coupJoue.getCoord().equals(listeAdjacentsSource.get(i))) {
                        coupJoue.setIrriguee(true);
                    }
                }

                // màj zoneJouee
                LOGGER.info("Liste des parcelles occupées :");
                zoneJouee.put(coupJoue.getCoord(), coupJoue);
                for (Map.Entry<Coordonnees, Parcelle> entry : zoneJouee.entrySet()) {
                    Coordonnees cle = entry.getKey();
                    LOGGER.info("" + cle);
                }

                // màj zoneDispo
                majZoneDispo(coupJoue);

                // màj listeZonesPosables
                listeZonesPosables.clear(); // on vide l'ancienne
                for (Map.Entry<Coordonnees, Integer> entry : zoneDispo.entrySet()) {
                    Coordonnees cle = entry.getKey();
                    Integer valeur = entry.getValue();
                    if (valeur >= 2)
                        listeZonesPosables.add(cle);
                }

                // On retire les parcelles occupées de la listeZonesPosables
                LOGGER.info("Liste des zones posables :");
                for (Map.Entry<Coordonnees, Parcelle> entry : zoneJouee.entrySet()) {
                    Coordonnees cle = entry.getKey();
                    supprimer(cle);
                }
                for (int i = 0; i < listeZonesPosables.size(); i++) {
                    LOGGER.info("" + listeZonesPosables.get(i));

                }

                // màj listeIrrigationsDispo
                majListeIrrigationsDispo(coupJoue);

                // màj listeIrrigationsPosables;
                majListeIrrigationsPosables();
            } else {
                throw new TricheException("impossible de jouer cette parcelle ici");
            }
        } else {
            throw new TricheException("impossible de poser 2 fois une parcelle");
        }
    }

    public ArrayList<Irrigation> getListeIrrigationsDispo() {
        return listeIrrigationsDispo;
    }

    public ArrayList<Irrigation> getListeIrrigationsPosables() {
        return listeIrrigationsPosables;
    }

    public ArrayList<Irrigation> getListeIrrigationsJouees() {
        return listeIrrigationsJouees;
    }

    public void majIrrigations(String nomBot, int choix) {
        listeIrrigationsJouees.add(listeIrrigationsPosables.get(choix));
        zoneJouee.get(listeIrrigationsPosables.get(choix).getCoord1()).setIrriguee(true);
        zoneJouee.get(listeIrrigationsPosables.get(choix).getCoord2()).setIrriguee(true);
        LOGGER.info(nomBot + " pose une irrigation en " + listeIrrigationsPosables.get(choix));
        listeIrrigationsDispo.remove(listeIrrigationsPosables.get(choix));
        listeIrrigationsPosables.remove(listeIrrigationsPosables.get(choix));
        majListeIrrigationsPosables();
    }

    public void majListeIrrigationsPosables() {
        for (int i = 0; i < listeIrrigationsDispo.size(); i++) {
            if ((estAProximiteDeLaSource(listeIrrigationsDispo.get(i)) || estAProximiteDuneAutreIrrigation(listeIrrigationsDispo.get(i))) && (!listeIrrigationsPosables.contains(listeIrrigationsDispo.get(i)))) {
                listeIrrigationsPosables.add(listeIrrigationsDispo.get(i));
            }
        }
    }

    private boolean estAProximiteDuneAutreIrrigation(Irrigation irrigation) {
        if (!listeIrrigationsJouees.isEmpty()) {
            for (int i = 0; i < listeIrrigationsJouees.size(); i++) {
                if ((irrigation.getCoord1().equals(listeIrrigationsJouees.get(i).getCoord1()) ||
                        irrigation.getCoord1().equals(listeIrrigationsJouees.get(i).getCoord2()) ||
                        irrigation.getCoord2().equals(listeIrrigationsJouees.get(i).getCoord1()) ||
                        irrigation.getCoord2().equals(listeIrrigationsJouees.get(i).getCoord2())) &&
                        estUnTriangle(irrigation.getCoord1(), irrigation.getCoord2(), listeIrrigationsJouees.get(i).getCoord1(), listeIrrigationsJouees.get(i).getCoord2())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean estUnTriangle(Coordonnees coord1, Coordonnees coord2, Coordonnees coord3, Coordonnees coord4) {
        // on retire le doublon, quelqu'il soit
        Coordonnees coordA, coordB, coordC;
        if (coord1.equals(coord3) || coord1.equals(coord4)) {
            coordA = coord2;

        } else {
            coordA = coord1;
        }
        coordB = coord3;
        coordC = coord4;

        if ((getAdjacents(coordA).contains(coordB) && getAdjacents(coordA).contains(coordC)) &&
                (getAdjacents(coordB).contains(coordA) && getAdjacents(coordB).contains(coordC)) &&
                (getAdjacents(coordC).contains(coordA) && getAdjacents(coordC).contains(coordB))) {
            return true;
        }
        return false;
    }

    private boolean estAProximiteDeLaSource(Irrigation irrigation) {
        if (getAdjacents(irrigation.getCoord1()).contains(new Coordonnees(0, 0, 0)) && getAdjacents(irrigation.getCoord2()).contains(new Coordonnees(0, 0, 0))) {
            return true;
        }
        return false;
    }

    private void majListeIrrigationsDispo(Parcelle coupJoue) {
        ArrayList<Coordonnees> listeProximite = new ArrayList<>();

        // On récupère la liste d'adjacence du coupJoue
        ArrayList<Coordonnees> adjacents = getAdjacents(coupJoue.getCoord());

        // On fait l'intersection avec zoneJouee
        for (int i = 0; i < adjacents.size(); i++) {
            if (zoneJouee.containsKey(adjacents.get(i))) {
                listeProximite.add(adjacents.get(i));
            }
        }

        // On supprime la source
        listeProximite.remove(new Coordonnees(0, 0, 0));

        // On ajoute chaque irrigation coupJoue / adjacent dans listeIrrigationsDispo
        for (int i = 0; i < listeProximite.size(); i++) {
            listeIrrigationsDispo.add(new Irrigation(coupJoue.getCoord(), listeProximite.get(i)));
        }
    }

    public void verifObjectifAccompli( FeuilleJoueur feuilleJoueur) {
        ArrayList<CartesObjectifs> mainObjectif = feuilleJoueur.getMainObjectif();
        for (Map.Entry<Coordonnees, Parcelle> entry : zoneJouee.entrySet()) {
            Parcelle valeur = entry.getValue();
            for (int i = 0; i < mainObjectif.size(); i++) {
                mainObjectif.get(i).verifObjectif(this, valeur, feuilleJoueur);
            }
            feuilleJoueur.setMainObjectif(mainObjectif);
        }
    }
}

