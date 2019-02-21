package takenoko.ia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import takenoko.entites.Entite;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.CarteObjectifPanda;
import takenoko.ressources.CartesObjectifs;
import takenoko.ressources.FeuilleJoueur;
import takenoko.ressources.Parcelle;
import takenoko.service.IClientService;
import takenoko.utilitaires.Coordonnees;
import takenoko.utilitaires.MainJoueur;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IAPanda implements IA {

    public static final Logger LOGGER = Logger.getLogger(IAPanda.class.getCanonicalName());
    private ArrayList<CartesObjectifs> mainObjectif = new ArrayList<>();
    private ArrayList<MainJoueur> mainObjectifValeur = new ArrayList<>();
    private FeuilleJoueur feuilleJoueur;
    private String nomBot;
    private static final int TAILLE_MAX_MAIN_OBJECTIF = 5;

    public FeuilleJoueur getFeuilleJoueur() {
        return feuilleJoueur;
    }

    @Autowired
    private IClientService iService;

    public IAPanda() {
        feuilleJoueur = new FeuilleJoueur();
        LOGGER.setLevel(Level.OFF);
    }

    @Override
    public void joue(LaPiocheParcelle piocheParcelle, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, Jardinier jardinier, Panda panda) {
        feuilleJoueur.initNbAction();
        verifObjectif(terrain);
        while (feuilleJoueur.getNbAction() > 0) {

            choisirAction(terrain, piocheParcelle, lesPiochesObjectif);
            faireAction(lesPiochesObjectif, piocheParcelle, terrain, jardinier, panda);
        }
        verifObjectif(terrain);
        LOGGER.info(nomBot + "%s possède " + feuilleJoueur.getPointsBot() + " points");
    }

    private void verifObjectif(Terrain terrain) {
        IA.verifObjectifAccompli(terrain, this);
        mainObjectifValeur.clear();
        for (CartesObjectifs c : mainObjectif) {
            mainObjectifValeur.add(new MainJoueur(c, 0));
        }
    }

    private void choisirAction(Terrain terrain, LaPiocheParcelle piocheParcelle, LesPiochesObjectif lesPiochesObjectif) {
        int newAction;
        do {
            if (terrain.getZoneJouee().size() > 1) {
                if (!uneCouleurDeChaque(terrain.getZoneJouee()) && feuilleJoueur.getActionChoisie() != 0 && !piocheParcelle.getPioche().isEmpty()) {
                    //sinon il pose une parcelle
                    newAction = 0;
                } else {
                    //sinon joue panda
                    newAction = 2;
                }
            } else {
                //il pose une parcelle
                newAction = 0;
            }
        } while (newAction == feuilleJoueur.getActionChoisie());
        feuilleJoueur.setActionChoisie(newAction);
    }

    //renvoi vrai si une parcelle de chaque couleur qui est irriguee et sans enclos
    private boolean uneCouleurDeChaque(HashMap<Coordonnees, Parcelle> zoneJoue) {
        return couleurParcellePresente(zoneJoue, Parcelle.Couleur.VERTE) && couleurParcellePresente(zoneJoue, Parcelle.Couleur.JAUNE) && couleurParcellePresente(zoneJoue, Parcelle.Couleur.ROSE);
    }

    //renvoi vrai si une parcelle est irriguee et sans enclos
    private boolean couleurParcellePresente(HashMap<Coordonnees, Parcelle> zoneJoue, Parcelle.Couleur couleur) {
        for (Map.Entry<Coordonnees, Parcelle> entry : zoneJoue.entrySet()) {
            Parcelle valeur = entry.getValue();
            if (valeur.getCouleur() == couleur && valeur.isIrriguee() && valeur.getEffet() != Parcelle.Effet.ENCLOS) {
                return true;
            }
        }
        return false;
    }

    private void faireAction(LesPiochesObjectif lesPiochesObjectif, LaPiocheParcelle piocheParcelle, Terrain terrain, Jardinier jardinier, Panda panda) {
        Parcelle coupJoue;
        if (feuilleJoueur.getActionChoisie() == 0) {
            // Bot pioche 3 parcelles, en choisit une aléatoirement et choisit une position aléatoire parmi la liste d'adjacences
            ArrayList<Parcelle> main = piocheParcelle.piocherParcelle();
            coupJoue = choisirParcelle(terrain.getZoneJouee(), main, piocheParcelle);
            //positionsCoupJoue
            coupJoue.setCoord(choisirPosition(terrain.getListeZonesPosables()));
            // Le terrain est mis à jour
            try {
                terrain.changements(coupJoue, this);
            } catch (TricheException e) {
                e.printStackTrace();
            }

        } else if (feuilleJoueur.getActionChoisie() == 1) {
            //Bot pioche un objectif panda
            try {
                lesPiochesObjectif.piocherUnObjectif(this, 2);
            } catch (TricheException e) {
                e.printStackTrace();
            }
            mainObjectifValeur.clear();
            for (CartesObjectifs c : mainObjectif) {
                mainObjectifValeur.add(new MainJoueur(c, 0));
            }
        } else if (feuilleJoueur.getActionChoisie() == 2) {
            //Bot déplace le panda
            valeurDeplacementPanda(terrain, panda.getDeplacementsPossible(terrain.getZoneJouee()));
            deplacementPanda(posCartePanda(), panda, jardinier, terrain, lesPiochesObjectif, piocheParcelle);
        } else if (feuilleJoueur.getActionChoisie() == 3) {
            //Bot déplace le jardinier
            valeurDeplacementJardinier(terrain, jardinier.getDeplacementsPossible(terrain.getZoneJouee()));
            deplacementJardinier(posCartePanda(), panda, jardinier, terrain, lesPiochesObjectif, piocheParcelle);
        }
    }

    private Coordonnees choisirPosition(ArrayList<Coordonnees> listeZonesPosables) {
        int choixPosition = 0;
        LOGGER.info(" et la positionne en " + listeZonesPosables.get(choixPosition) + ".");
        return listeZonesPosables.get(choixPosition);
    }

    //renvoi les couleurs manquante poun un objectif
    private ArrayList<Parcelle.Couleur> choixCouleur(MainJoueur m) {
        ArrayList<Parcelle.Couleur> couleursPossible = new ArrayList<>();
        if (m.getCartesObjectifs().getCouleur2() != null) {
            if (feuilleJoueur.getNbBambouRose() == 0) {
                couleursPossible.add(m.getCartesObjectifs().getCouleur2());
                m.incNbBambouManquant();
            }
            if (feuilleJoueur.getNbBambouVert() == 0) {
                couleursPossible.add(m.getCartesObjectifs().getCouleur());
                m.incNbBambouManquant();
            }
            if (feuilleJoueur.getNb_bambou_jaune() == 0) {
                couleursPossible.add(m.getCartesObjectifs().getCouleur3());
                m.incNbBambouManquant();
            }
        } else {
            //par defaut on fait rien
            int nbBamobu = 2;
            if (m.getCartesObjectifs().getCouleur() == Parcelle.Couleur.VERTE) {
                nbBamobu = feuilleJoueur.getNbBambouVert();

            } else if (m.getCartesObjectifs().getCouleur() == Parcelle.Couleur.ROSE) {
                nbBamobu = feuilleJoueur.getNbBambouRose();

            } else if (m.getCartesObjectifs().getCouleur() == Parcelle.Couleur.JAUNE) {
                nbBamobu = feuilleJoueur.getNb_bambou_jaune();

            }
            switch (nbBamobu) {
                case 0:
                    m.incNbBambouManquant();
                    m.incNbBambouManquant();
                    break;
                case 1:
                    m.incNbBambouManquant();
                    break;
            }
            couleursPossible.add(m.getCartesObjectifs().getCouleur());
        }
        return couleursPossible;
    }

    private void valeurDeplacementPanda(Terrain terrain, ArrayList<Coordonnees> zoneDeDeplacement) {
        for (MainJoueur m : mainObjectifValeur) {
            ArrayList<Parcelle.Couleur> couleursPossible = choixCouleur(m);

            ArrayList<Coordonnees> coordonneesPossible = deplacementPandaPossible(terrain, zoneDeDeplacement, couleursPossible);

            if (!coordonneesPossible.isEmpty()) {
                m.incCout();
                if (m.getNbBambouManquant() == 1) {
                    m.incCout();
                }
                m.setCoordonneesPandaPossible(coordonneesPossible);
            } else {
                m.resetCout();
                m.resetNbBambouManquant();
                m.getCoordonneesPandaPossible().clear();
            }
        }
    }

    private void valeurDeplacementJardinier(Terrain terrain, ArrayList<Coordonnees> zoneDeDeplacement) {
        for (MainJoueur m : mainObjectifValeur) {
            ArrayList<Parcelle.Couleur> couleursPossible = choixCouleur(m);

            ArrayList<Coordonnees> coordonneesPossible = deplacementJardinierPossibe(terrain, zoneDeDeplacement, couleursPossible);

            if (!coordonneesPossible.isEmpty()) {
                m.incCout();
                if (m.getNbBambouManquant() == 1) {
                    m.incCout();
                }
                m.setCoordonneesJardinierPossible(coordonneesPossible);
            } else {
                m.resetCout();
                m.resetNbBambouManquant();
                m.getCoordonneesJardinierPossible().clear();
            }
        }
    }

    private Parcelle choisirParcelle(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Parcelle> troisParcelles, LaPiocheParcelle piocheParcelle) {
        Parcelle p;
        LOGGER.info(nomBot + " peux choisir entre :");

        for (int j = 0; j < troisParcelles.size(); j++) {
            LOGGER.info("" + troisParcelles.get(j).getCouleur() + " " + troisParcelles.get(j).getEffet());
        }
        int choixParcelle = 0;
        //recupere une parcelle de la bonne couleur si pas d'objectif on cherche a mettre une parcelle de couleur non presente
        int nbEnclos = 0;
        for (Parcelle c : troisParcelles) {
            if (c.getEffet() != Parcelle.Effet.ENCLOS) {
                if ((!couleurParcellePresente(zoneJoue, Parcelle.Couleur.VERTE) && c.getCouleur() == Parcelle.Couleur.VERTE) ||
                        (!couleurParcellePresente(zoneJoue, Parcelle.Couleur.ROSE) && c.getCouleur() == Parcelle.Couleur.ROSE) ||
                        (!couleurParcellePresente(zoneJoue, Parcelle.Couleur.JAUNE) && c.getCouleur() == Parcelle.Couleur.JAUNE)) {
                    choixParcelle = troisParcelles.indexOf(c);
                }
                if (c.getEffet() == Parcelle.Effet.PUIT) {
                    break;
                }
            } else {
                // si que des enclos
                nbEnclos++;
            }
        }
        if (nbEnclos == 3) {
            choixParcelle = 0;
        }
        LOGGER.info(nomBot + "choisit la parcelle n°" + (choixParcelle + 1));
        p = troisParcelles.get(choixParcelle);
        piocheParcelle.reposeSousLaPioche(troisParcelles, choixParcelle);
        return p;
    }

    private void deplacementPanda(int j, Panda panda, Jardinier jardinier, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, LaPiocheParcelle laPiocheParcelle) {
        //si un deplacement interessant
        if (j != 15 && mainObjectifValeur.get(j).getCout() != 0) {
            try {
                panda.deplacerEntite(mainObjectifValeur.get(j).getCoordonneesPandaPossible().get(0), this);
                LOGGER.info(nomBot + " a déplacé le panda a la coordonnée : " + panda.getCoordonnees());
            } catch (TricheException e) {
                LOGGER.warning(e.getMessage());
            }
            //si il a une deuxieme action
            verifObjectif(terrain);
            if (mainObjectif.size() < TAILLE_MAX_MAIN_OBJECTIF && feuilleJoueur.getActionChoisie() != 1 && !lesPiochesObjectif.isEmptyPiocheObjectifsPanda() && feuilleJoueur.getNbAction() == 1) {
                feuilleJoueur.setActionChoisie(1);
                faireAction(lesPiochesObjectif, laPiocheParcelle, terrain, jardinier, panda);
            } else if (feuilleJoueur.getActionChoisie() != 3 && feuilleJoueur.getNbAction() == 1) {
                feuilleJoueur.setActionChoisie(3);
                faireAction(lesPiochesObjectif, laPiocheParcelle, terrain, jardinier, panda);
            }
        } else {
            //sinon on essaye le jardinier
            feuilleJoueur.setActionChoisie(3);
            faireAction(lesPiochesObjectif, laPiocheParcelle, terrain, jardinier, panda);
        }
    }

    private void deplacementJardinier(int j, Panda panda, Jardinier jardinier, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, LaPiocheParcelle laPiocheParcelle) {
        //si un deplacement interessant
        if (j != 15 && mainObjectifValeur.get(j).getCout() != 0) {
            try {
                jardinier.deplacerEntite(mainObjectifValeur.get(j).getCoordonneesJardinierPossible().get(0), this);
                LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée : " + jardinier.getCoordonnees());
            } catch (TricheException e) {
                aucunCoupInterressant(panda, jardinier, terrain, lesPiochesObjectif, laPiocheParcelle);
            }
        } else {
            //sinon
            aucunCoupInterressant(panda, jardinier, terrain, lesPiochesObjectif, laPiocheParcelle);
        }
    }

    private void aucunCoupInterressant(Panda panda, Jardinier jardinier, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, LaPiocheParcelle laPiocheParcelle) {
        verifObjectif(terrain);
        if (mainObjectif.size() < TAILLE_MAX_MAIN_OBJECTIF && feuilleJoueur.getActionChoisie() != 1 && !lesPiochesObjectif.isEmptyPiocheObjectifsPanda() && feuilleJoueur.getNbAction() == 1) {
            feuilleJoueur.setActionChoisie(1);
            faireAction(lesPiochesObjectif, laPiocheParcelle, terrain, jardinier, panda);
        } else {
            try {
                Coordonnees c = deplacementAvancePanda(terrain, panda);
                if (c != null) {
                    panda.deplacerEntite(c, this);
                    LOGGER.info(nomBot + " a déplacé le panda a la coordonnée (avancee): " + panda.getCoordonnees());
                } else {
                    c = deplacementAvanceJardinier(terrain, jardinier);
                    if (c != null) {
                        try {
                            jardinier.deplacerEntite(c, this);
                            LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée (avancee): " + jardinier.getCoordonnees());
                        } catch (TricheException x) {
                            feuilleJoueur.decNbACtion();
                        }
                    } else {
                        feuilleJoueur.decNbACtion();
                    }
                }
            } catch (TricheException e) {
                Coordonnees c = deplacementAvanceJardinier(terrain, jardinier);
                if (c != null) {
                    try {
                        jardinier.deplacerEntite(c, this);
                        LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée (avancee) : " + jardinier.getCoordonnees());
                    } catch (TricheException x) {
                        feuilleJoueur.decNbACtion();
                    }
                } else {
                    feuilleJoueur.decNbACtion();
                }
            }
        }
    }

    private Coordonnees deplacementAvancePanda(Terrain terrain, Panda panda) {
        Coordonnees c = null;
        valeurDeplacementPanda(terrain, listZoneJouee(terrain));
        int posCarte = posCartePanda();
        ArrayList<Coordonnees> coordonnees;
        if (posCarte != 15 && mainObjectifValeur.get(posCarte).getCout() != 0) {
            ArrayList<Coordonnees> cooParcelle = new ArrayList<>();
            //coordonnes ou panda peut aller (sur tout le terrain)
            coordonnees = mainObjectifValeur.get(posCarte).getCoordonneesPandaPossible();
            for (Coordonnees co : coordonnees) {
                //on récupère  les coordonnes qui nous permettent d'aller sur celle voulu
                co.deplacementPossible(terrain.getZoneJouee(), cooParcelle);
            }
            for (Coordonnees co : panda.getDeplacementsPossible(terrain.getZoneJouee())) {
                if (cooParcelle.contains(co)) {
                    //si le panda peut si rendre
                    c = co;
                    break;
                }
            }
        } else if (!panda.getCoordonnees().equals(new Coordonnees(0, 0, 0))) {
            c = procheDuCentre(panda.getDeplacementsPossible(terrain.getZoneJouee()), panda);
        }
        return c;
    }

    private Coordonnees deplacementAvanceJardinier(Terrain terrain, Jardinier jardinier) {
        Coordonnees c = null;
        valeurDeplacementJardinier(terrain, listZoneJouee(terrain));
        int posCarte = posCartePanda();
        ArrayList<Coordonnees> coordonnees;
        if (posCarte != 15 && mainObjectifValeur.get(posCarte).getCout() != 0) {
            ArrayList<Coordonnees> cooParcelle = new ArrayList<>();
            coordonnees = mainObjectifValeur.get(posCarte).getCoordonneesJardinierPossible();
            for (Coordonnees co : coordonnees) {
                //on récupère  les coordonnes qui nous permettent d'aller sur celle voulu
                co.deplacementPossible(terrain.getZoneJouee(), cooParcelle);
            }
            for (Coordonnees co : jardinier.getDeplacementsPossible(terrain.getZoneJouee())) {
                if (cooParcelle.contains(co)) {
                    //si le jardinier peut si rendre
                    c = co;
                    break;
                }
            }
        } else if (!jardinier.getCoordonnees().equals(new Coordonnees(0, 0, 0))) {
            c = procheDuCentre(jardinier.getDeplacementsPossible(terrain.getZoneJouee()), jardinier);
        }
        return c;
    }

    private Coordonnees procheDuCentre(ArrayList<Coordonnees> deplacementPossible, Entite entite) {
        Coordonnees choix = null;
        int[][] tab = new int[deplacementPossible.size()][1];
        for (int i = 0; i < deplacementPossible.size(); i++) {
            tab[i][0] = Math.abs(deplacementPossible.get(i).getX()) + Math.abs(deplacementPossible.get(i).getY()) + Math.abs(deplacementPossible.get(i).getZ());
        }
        int max = Math.abs(entite.getCoordonnees().getX()) + Math.abs(entite.getCoordonnees().getY()) + Math.abs(entite.getCoordonnees().getZ());
        for (int i = 0; i < deplacementPossible.size(); i++) {
            if (tab[i][0] <= max) {
                choix = deplacementPossible.get(i);
            }
        }
        return choix;
    }

    private ArrayList<Coordonnees> deplacementPandaPossible(Terrain terrain, ArrayList<Coordonnees> deplacementsPossibles, ArrayList<Parcelle.Couleur> couleurPossible) {
        ArrayList<Coordonnees> coordonneesPossible = new ArrayList<>();
        for (Parcelle.Couleur couleur : couleurPossible) {
            for (Coordonnees c : deplacementsPossibles) {
                if (terrain.getZoneJouee().get(c).getCouleur() == couleur && terrain.getZoneJouee().get(c).getEffet() != Parcelle.Effet.ENCLOS && terrain.getZoneJouee().get(c).getBambou() != 0) {
                    coordonneesPossible.add(c);
                }
            }
        }
        return coordonneesPossible;
    }

    private ArrayList<Coordonnees> deplacementJardinierPossibe(Terrain terrain, ArrayList<Coordonnees> deplacementsPossibles, ArrayList<Parcelle.Couleur> couleurPossible) {
        ArrayList<Coordonnees> coordonneesPossible = new ArrayList<>();
        for (Parcelle.Couleur couleur : couleurPossible) {
            for (Coordonnees c : deplacementsPossibles) {
                if (terrain.getZoneJouee().get(c).getCouleur() == couleur && terrain.getZoneJouee().get(c).getEffet() != Parcelle.Effet.ENCLOS && terrain.getZoneJouee().get(c).getBambou() <= 2 && terrain.getZoneJouee().get(c).isIrriguee()) {
                    coordonneesPossible.add(c);
                }
            }
        }
        return coordonneesPossible;
    }


    private int posCartePanda() {
        //15 = aucune carte ayant un coup a jouer
        int j = 15;
        int max = 0;
        for (MainJoueur c : mainObjectifValeur) {
            if (c.getCartesObjectifs() instanceof CarteObjectifPanda) {
                if (max < c.getCout()) {
                    max = c.getCout();
                }
            }
        }
        for (MainJoueur c : mainObjectifValeur) {
            if (c.getCartesObjectifs() instanceof CarteObjectifPanda && c.getCout() == max) {
                j = mainObjectifValeur.indexOf(c);
                break;
            }
        }
        return j;
    }

    //transforme la hashmap en arraylist
    private ArrayList<Coordonnees> listZoneJouee(Terrain terrain) {
        ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        for (Map.Entry<Coordonnees, Parcelle> entry : terrain.getZoneJouee().entrySet()) {
            Coordonnees c = entry.getKey();
            coordonnees.add(c);
        }
        return coordonnees;
    }

    public ArrayList<CartesObjectifs> getMainObjectif() {
        return mainObjectif;
    }


    public void setMainObjectif(ArrayList<CartesObjectifs> mainObjectif) {
        this.mainObjectif = mainObjectif;
    }

    @Override
    public String getNomBot() {
        return nomBot;
    }

    public void setNomBot(String nomBot) {
        this.nomBot = nomBot;
    }
}
