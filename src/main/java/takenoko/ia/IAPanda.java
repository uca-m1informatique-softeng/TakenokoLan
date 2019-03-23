package takenoko.ia;

import org.springframework.stereotype.Component;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.*;
import takenoko.service.impl.ClientService;
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
    private String nomBot;
    private static final int TAILLE_MAX_MAIN_OBJECTIF = 5;
    private ClientService iService;

    public FeuilleJoueur getFeuilleJoueur() {
        return iService.getFeuilleJoueur();
    }


    public IAPanda() {
        iService = new ClientService();
        LOGGER.setLevel(Level.OFF);
    }

    @Override
    public void joue(LaPiocheParcelle piocheParcelle, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, Jardinier jardinier, Panda panda) {
        iService.feuilleJoueurInitNbAction();
        verifObjectif();

        while (iService.feuilleJoueurGetNbAction() > 0) {
            choisirAction(terrain);
            faireAction(lesPiochesObjectif, terrain);
        }
        verifObjectif();
        //LOGGER.info(nomBot + "%s possède " + iService.getFeuilleJoueur().getPointsBot() + " points");
    }

    private void verifObjectif() {
        iService.verifObjectifAccompli();
        mainObjectif = iService.feuilleJoueurGetMainObjectif();
        mainObjectifValeur.clear();
        for (CartesObjectifs c : mainObjectif) {
            mainObjectifValeur.add(new MainJoueur(c, 0));
        }
    }

    private void choisirAction(Terrain terrain) {
        int newAction;
        do {
            if (iService.getZoneJouee().size() > 1) {
                if (!uneCouleurDeChaque(iService.getZoneJouee()) && iService.feuilleJoueurGetActionChoisie() != 0 && !iService.piocheParcelleIsEmpty()) {
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
        } while (newAction == iService.feuilleJoueurGetActionChoisie());
        iService.feuilleJoueurSetActionChoisie(newAction);
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

    private void faireAction(LesPiochesObjectif lesPiochesObjectif, Terrain terrain) {
        Parcelle coupJoue;
        if (iService.feuilleJoueurGetActionChoisie() == 0) {
            // Bot pioche 3 parcelles, en choisit une aléatoirement et choisit une position aléatoire parmi la liste d'adjacences
            coupJoue = choisirParcelle(iService.getZoneJouee(), iService.piocher());
            //positionsCoupJoue
            coupJoue.setCoord(choisirPosition(terrain.getListeZonesPosables()));
            // Le terrain est mis à jour
            try {
                iService.poserParcelle(coupJoue);
            } catch (TricheException e) {
                e.printStackTrace();
            }

        } else if (iService.feuilleJoueurGetActionChoisie() == 1) {
            //Bot pioche un objectif panda
            try {
                iService.piocherUnObjectif(2);
            } catch (TricheException e) {
                e.printStackTrace();
            }
            mainObjectif = iService.feuilleJoueurGetMainObjectif();
            mainObjectifValeur.clear();
            for (CartesObjectifs c : mainObjectif) {
                mainObjectifValeur.add(new MainJoueur(c, 0));
            }
        } else if (iService.feuilleJoueurGetActionChoisie() == 2) {
            //Bot déplace le panda
            valeurDeplacementPanda(terrain, iService.pandaGetDeplacementsPossible());
            deplacementPanda(posCartePanda(), terrain, lesPiochesObjectif);
        } else if (iService.feuilleJoueurGetActionChoisie() == 3) {
            //Bot déplace le jardinier
            valeurDeplacementJardinier(terrain, iService.jardinierGetDeplacementsPossible());
            deplacementJardinier(posCartePanda(), terrain, lesPiochesObjectif);
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
            if (iService.feuilleJoueurGetNbBambouRose() == 0) {
                couleursPossible.add(m.getCartesObjectifs().getCouleur2());
                m.incNbBambouManquant();
            }
            if (iService.feuilleJoueurGetNbBambouVert() == 0) {
                couleursPossible.add(m.getCartesObjectifs().getCouleur());
                m.incNbBambouManquant();
            }
            if (iService.feuilleJoueurGetNbBambouJaune() == 0) {
                couleursPossible.add(m.getCartesObjectifs().getCouleur3());
                m.incNbBambouManquant();
            }
        } else {
            //par defaut on fait rien
            int nbBamobu = 2;
            if (m.getCartesObjectifs().getCouleur() == Parcelle.Couleur.VERTE) {
                nbBamobu = iService.feuilleJoueurGetNbBambouVert();

            } else if (m.getCartesObjectifs().getCouleur() == Parcelle.Couleur.ROSE) {
                nbBamobu = iService.feuilleJoueurGetNbBambouRose();

            } else if (m.getCartesObjectifs().getCouleur() == Parcelle.Couleur.JAUNE) {
                nbBamobu = iService.feuilleJoueurGetNbBambouJaune();

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

    private Parcelle choisirParcelle(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Parcelle> troisParcelles) {
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
        troisParcelles.remove(p);
        iService.reposeSousLaPioche(troisParcelles);
        return p;
    }

    private void deplacementPanda(int j, Terrain terrain, LesPiochesObjectif lesPiochesObjectif) {
        //si un deplacement interessant
        if (j != 15 && mainObjectifValeur.get(j).getCout() != 0) {
            try {
                iService.deplacerPanda(mainObjectifValeur.get(j).getCoordonneesPandaPossible().get(0));
                LOGGER.info(nomBot + " a déplacé le panda a la coordonnée : " + iService.pandaGetCoordonnees());
            } catch (TricheException e) {
                LOGGER.warning(e.getMessage());
            }
            //si il a une deuxieme action
            verifObjectif();
            if (mainObjectif.size() < TAILLE_MAX_MAIN_OBJECTIF && iService.feuilleJoueurGetActionChoisie() != 1 && !iService.piochePandaIsEmpty() && iService.feuilleJoueurGetNbAction() == 1) {
                iService.feuilleJoueurSetActionChoisie(1);
                faireAction(lesPiochesObjectif, terrain);
            } else if (iService.feuilleJoueurGetActionChoisie() != 3 && iService.feuilleJoueurGetNbAction() == 1) {
                iService.feuilleJoueurSetActionChoisie(3);
                faireAction(lesPiochesObjectif, terrain);
            }
        } else {
            //sinon on essaye le jardinier
            iService.feuilleJoueurSetActionChoisie(3);
            faireAction(lesPiochesObjectif, terrain);
        }
    }

    private void deplacementJardinier(int j, Terrain terrain, LesPiochesObjectif lesPiochesObjectif) {
        //si un deplacement interessant
        if (j != 15 && mainObjectifValeur.get(j).getCout() != 0) {
            try {
                iService.deplacerJardinier(mainObjectifValeur.get(j).getCoordonneesJardinierPossible().get(0));
                LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée : " + iService.jardinierGetCoordonnees());
            } catch (TricheException e) {
                aucunCoupInterressant(terrain, lesPiochesObjectif);
            }
        } else {
            //sinon
            aucunCoupInterressant(terrain, lesPiochesObjectif);
        }
    }

    private void aucunCoupInterressant(Terrain terrain, LesPiochesObjectif lesPiochesObjectif) {
        verifObjectif();
        if (mainObjectif.size() < TAILLE_MAX_MAIN_OBJECTIF && iService.feuilleJoueurGetActionChoisie() != 1 && !iService.piochePandaIsEmpty() && iService.feuilleJoueurGetNbAction() == 1) {
            iService.feuilleJoueurSetActionChoisie(1);
            faireAction(lesPiochesObjectif, terrain);
        } else {
            try {
                Coordonnees c = deplacementAvancePanda(terrain);
                if (c != null) {
                    iService.deplacerPanda(c);
                    LOGGER.info(nomBot + " a déplacé le panda a la coordonnée (avancee): " + iService.pandaGetCoordonnees());
                } else {
                    c = deplacementAvanceJardinier(terrain);
                    if (c != null) {
                        try {
                            iService.deplacerJardinier(c);
                            LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée (avancee): " + iService.jardinierGetCoordonnees());
                        } catch (TricheException x) {
                            iService.feuilleJoueurDecNbACtion();
                        }
                    } else {
                        iService.feuilleJoueurDecNbACtion();
                    }
                }
            } catch (TricheException e) {
                Coordonnees c = deplacementAvanceJardinier(terrain);
                if (c != null) {
                    try {
                        iService.deplacerJardinier(c);
                        LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée (avancee) : " + iService.jardinierGetCoordonnees());
                    } catch (TricheException x) {
                        iService.feuilleJoueurDecNbACtion();
                    }
                } else {
                    iService.feuilleJoueurDecNbACtion();
                }
            }
        }
    }

    private Coordonnees deplacementAvancePanda(Terrain terrain) {
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
                co.deplacementPossible(iService.getZoneJouee(), cooParcelle);
            }
            for (Coordonnees co : iService.pandaGetDeplacementsPossible()) {
                if (cooParcelle.contains(co)) {
                    //si le panda peut si rendre
                    c = co;
                    break;
                }
            }
        } else if (!iService.pandaGetCoordonnees().equals(new Coordonnees(0, 0, 0))) {
            c = procheDuCentre(iService.pandaGetDeplacementsPossible(), iService.pandaGetCoordonnees());
        }
        return c;
    }

    private Coordonnees deplacementAvanceJardinier(Terrain terrain) {
        Coordonnees c = null;
        valeurDeplacementJardinier(terrain, listZoneJouee(terrain));
        int posCarte = posCartePanda();
        ArrayList<Coordonnees> coordonnees;
        if (posCarte != 15 && mainObjectifValeur.get(posCarte).getCout() != 0) {
            ArrayList<Coordonnees> cooParcelle = new ArrayList<>();
            coordonnees = mainObjectifValeur.get(posCarte).getCoordonneesJardinierPossible();
            for (Coordonnees co : coordonnees) {
                //on récupère  les coordonnes qui nous permettent d'aller sur celle voulu
                co.deplacementPossible(iService.getZoneJouee(), cooParcelle);
            }
            for (Coordonnees co : iService.jardinierGetDeplacementsPossible()) {
                if (cooParcelle.contains(co)) {
                    //si le jardinier peut si rendre
                    c = co;
                    break;
                }
            }
        } else if (!iService.jardinierGetCoordonnees().equals(new Coordonnees(0, 0, 0))) {
            c = procheDuCentre(iService.jardinierGetDeplacementsPossible(), iService.jardinierGetCoordonnees());
        }
        return c;
    }


    private Coordonnees procheDuCentre(ArrayList<Coordonnees> deplacementPossible, Coordonnees coordonnees) {
        Coordonnees choix = null;
        int[][] tab = new int[deplacementPossible.size()][1];
        for (int i = 0; i < deplacementPossible.size(); i++) {
            tab[i][0] = Math.abs(deplacementPossible.get(i).getX()) + Math.abs(deplacementPossible.get(i).getY()) + Math.abs(deplacementPossible.get(i).getZ());
        }
        int max = Math.abs(coordonnees.getX()) + Math.abs(coordonnees.getY()) + Math.abs(coordonnees.getZ());
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
                if (iService.getParcelle(c).getCouleur() == couleur && iService.getParcelle(c).getEffet() != Parcelle.Effet.ENCLOS && iService.getParcelle(c).getBambou() != 0) {
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
                if (iService.getParcelle(c).getCouleur() == couleur && iService.getParcelle(c).getEffet() != Parcelle.Effet.ENCLOS && iService.getParcelle(c).getBambou() <= 2 && iService.getParcelle(c).isIrriguee()) {
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
        for (Map.Entry<Coordonnees, Parcelle> entry : iService.getZoneJouee().entrySet()) {
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

    public void setiService(ClientService iService) {
        this.iService = iService;
    }
}
