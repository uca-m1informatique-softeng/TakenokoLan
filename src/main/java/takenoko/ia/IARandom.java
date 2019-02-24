package takenoko.ia;

import org.springframework.stereotype.Component;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.CartesObjectifs;
import takenoko.ressources.FeuilleJoueur;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IARandom implements IA {

    public static final Logger LOGGER = Logger.getLogger(IARandom.class.getCanonicalName());

    private Random rand = new Random();
    private ArrayList<CartesObjectifs> mainObjectif = new ArrayList<>();
    private static final int TAILLE_MAX_MAIN_OBJECTIF = 5;
    private String nomBot;

    private FeuilleJoueur feuilleJoueur;

    public FeuilleJoueur getFeuilleJoueur() {
        return feuilleJoueur;
    }

    public IARandom() {
        feuilleJoueur = new FeuilleJoueur();
        LOGGER.setLevel(Level.OFF);
    }

    public void joue(LaPiocheParcelle piocheParcelle, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, Jardinier jardinier, Panda panda) {
        feuilleJoueur.initNbAction();

        while (feuilleJoueur.getNbAction() > 0) {
            choisirAction(terrain);
            faireAction(lesPiochesObjectif, piocheParcelle, terrain, jardinier, panda);
        }

        if (feuilleJoueur.getNb_irrigation() != 0) {
            placerIrrigations(terrain);
        }
        IA.verifObjectifAccompli(terrain, this);
        LOGGER.info(nomBot + " possède " + feuilleJoueur.getPointsBot() + " points");
    }

    private void placerIrrigations(Terrain terrain) {
        // Bob choisit ou non de vouloir poser des irrigations
        int min = Math.min(feuilleJoueur.getNb_irrigation(), terrain.getListeIrrigationsPosables().size());
        if (rand.nextBoolean() && min > 0) {
            // Bob choisit combien en poser sachant que ce sera le minimum entre le nombre d'irrigations qu'il a et la taille de la liste des irrigations disponibles
            int nbrIrrigationsAPoser;
            if (min == 1) {
                nbrIrrigationsAPoser = 1;
            } else {
                nbrIrrigationsAPoser = rand.nextInt(min - 1) + 1;
            }

            for (int i = 0; i < nbrIrrigationsAPoser; i++) {
                // Bob choisit aléatoirement une irrigation
                int choix = rand.nextInt(terrain.getListeIrrigationsPosables().size());

                // maj feuilleJoueur
                feuilleJoueur.decIrrigation();

                // maj terrain
                terrain.majIrrigations(nomBot, choix);
            }
        }
    }

    private Parcelle choisirParcelle(ArrayList<Parcelle> troisParcelles, LaPiocheParcelle piocheParcelle) {
        Parcelle p;
        LOGGER.info(nomBot + " peux choisir entre :");
        int j = 0;
        for (; j < troisParcelles.size(); j++) {
            LOGGER.info("" + troisParcelles.get(j).getCouleur() + " " + troisParcelles.get(j).getEffet());
        }
        int choixParcelle = rand.nextInt(j);
        LOGGER.info(nomBot + "choisit la parcelle n°" + (choixParcelle + 1));
        p = troisParcelles.get(choixParcelle);
        troisParcelles.remove(p);
        piocheParcelle.reposeSousLaPioche(troisParcelles);
        return p;
    }

    private Coordonnees choisirPosition(ArrayList<Coordonnees> listeZonesPosables) {
        int choixPosition = rand.nextInt(listeZonesPosables.size());
        LOGGER.info(" et la positionne en " + listeZonesPosables.get(choixPosition) + ".");
        return listeZonesPosables.get(choixPosition);
    }

    public ArrayList<CartesObjectifs> getMainObjectif() {
        return mainObjectif;
    }

    public void setMainObjectif(ArrayList<CartesObjectifs> mainObjectif) {
        this.mainObjectif = mainObjectif;
    }

    private void choisirAction(Terrain terrain) {
        int newAction;
        do {
            if (terrain.getZoneJouee().size() > 1) {
                newAction = rand.nextInt(5);
            } else {
                newAction = rand.nextInt(2);
            }
        } while (newAction == feuilleJoueur.getPrecedant());
        feuilleJoueur.setActionChoisie(newAction);
    }

    private Coordonnees choixDeplacement(ArrayList<Coordonnees> deplacementsPossibles) {
        return deplacementsPossibles.get(rand.nextInt(deplacementsPossibles.size()));
    }

    private void faireAction(LesPiochesObjectif lesPiochesObjectif, LaPiocheParcelle piocheParcelle, Terrain terrain, Jardinier jardinier, Panda panda) {
        try {
            Parcelle coupJoue;
            if (feuilleJoueur.getActionChoisie() == 0) {
                // Bot pioche 3 parcelles, en choisit une aléatoirement et choisit une position aléatoire parmi la liste d'adjacences
                if (piocheParcelle.getPioche().size() > 0) {
                    ArrayList<Parcelle> main = piocheParcelle.piocherParcelle();
                    coupJoue = choisirParcelle(main, piocheParcelle);
                    //positionsCoupJoue
                    coupJoue.setCoord(choisirPosition(terrain.getListeZonesPosables()));
                    // Le terrain est mis à jour
                    terrain.changements(coupJoue, this);
                } else {
                    LOGGER.info("pioche parcelle vide");
                    choisirAction(terrain);
                    faireAction(lesPiochesObjectif, piocheParcelle, terrain, jardinier, panda);
                }
            } else if (feuilleJoueur.getActionChoisie() == 1) {
                //Bot pioche un objectif
                if (mainObjectif.size() < TAILLE_MAX_MAIN_OBJECTIF) {
                    lesPiochesObjectif.piocherUnObjectif(this, 2 /*rand.nextInt(3)*/);
                } else {
                    LOGGER.info("main objectif taille max");
                    choisirAction(terrain);
                    faireAction(lesPiochesObjectif, piocheParcelle, terrain, jardinier, panda);
                }
            } else if (feuilleJoueur.getActionChoisie() == 2) {
                //Bot déplace le panda
                panda.deplacerEntite(choixDeplacement(panda.getDeplacementsPossible(terrain.getZoneJouee())), this);
                LOGGER.warning(nomBot + " a déplacé le panda a la coordonnée :  " + panda.getCoordonnees());
            } else if (feuilleJoueur.getActionChoisie() == 3) {
                //Bot déplace le jardinier
                jardinier.deplacerEntite(choixDeplacement(jardinier.getDeplacementsPossible(terrain.getZoneJouee())), this);
                LOGGER.warning(nomBot + " a déplacé le jardinier a la coordonnée :  " + jardinier.getCoordonnees());
            }
            // piocher une irrigation
            else if (feuilleJoueur.getActionChoisie() == 4) {
                feuilleJoueur.incIrrigation();
                LOGGER.warning(nomBot + " a pioché une irrigation et en possède à présent " + feuilleJoueur.getNb_irrigation());
            }
        } catch (TricheException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public String getNomBot() {
        return nomBot;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public void setNomBot(String nomBot) {
        this.nomBot = nomBot;
    }
}

