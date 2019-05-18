package joueur.ia;

import commun.ressources.CarteObjectifPanda;
import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.Parcelle;
import commun.triche.TricheException;
import joueur.service.IClientService;
import joueur.service.impl.ClientService;
import joueur.utilitaires.MainJoueur;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IAPanda implements ApplicationListener<ApplicationReadyEvent> {


    public static final Logger LOGGER = Logger.getLogger(IAPanda.class.getCanonicalName());
    private ArrayList<CartesObjectifs> mainObjectif = new ArrayList<>();
    private ArrayList<MainJoueur> mainObjectifValeur = new ArrayList<>();
    private String nomBot;
    private static final int TAILLE_MAX_MAIN_OBJECTIF = 5;
    private IClientService iService;


    @Autowired
    Environment environment;
    String getPort(){
        return environment.getProperty("local.server.port");
    }


    @Autowired
    public IAPanda() {
        nomBot = RandomStringUtils.randomAlphabetic(10);
        iService = new ClientService();
        LOGGER.setLevel(Level.OFF);

    }



    /**
     * Cet événement est exécuté le plus tard possible pour indiquer
     * que l'application est prête à repondre aux demandes.
     */

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {

           //int[] tab = connect("192.168.99.100", "8080", InetAddress.getLocalHost().getHostAddress(), getPort());//pour docker local
           // int[] tab = connect("localhost", "8080", InetAddress.getLocalHost().getHostAddress(), getPort());
           int[] tab = connect("172.18.0.2", "8080", InetAddress.getLocalHost().getHostAddress(), getPort());//pour travis
            System.out.println("new player connecté à la partie num : " + tab[0] + " en tant que joueur : " + tab[1]);
        } catch (Exception e) {
        }
        launch();
    }

    private int[] connect(String serveurHost, String serveurPort, String joueurHost, String joueurPort) {
        boolean alive;
        RestTemplate restTemplate = new RestTemplate();
        do {
            try {
                restTemplate.exchange("http://" + serveurHost + ":" + serveurPort + "/alive", HttpMethod.GET, null, String.class);
                alive = true;
            } catch (Exception e) {
                System.out.println("Serveur inaccessible");
                alive = false;
                //Pour attendre 10s
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException a) {
                }
            }
        } while (!alive);
        return iService.connect(nomBot, serveurHost, serveurPort, joueurHost, joueurPort);
    }

    private void launch() {
        iService.launch();
    }


    public void joue() {
        iService.feuilleJoueurInitNbAction();
        verifObjectif();

        while (iService.feuilleJoueurGetNbAction() > 0) {
            choisirAction();
            faireAction();
        }
        verifObjectif();
    }

    private void verifObjectif() {
        iService.verifObjectifAccompli();
        mainObjectif = iService.feuilleJoueurGetMainObjectif();
        mainObjectifValeur.clear();
        for (CartesObjectifs c : mainObjectif) {
            mainObjectifValeur.add(new MainJoueur(c, 0));
        }
    }

    private void choisirAction() {
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
    private boolean uneCouleurDeChaque(ArrayList<Parcelle> zoneJoue) {
        return couleurParcellePresente(zoneJoue, Parcelle.Couleur.VERTE) && couleurParcellePresente(zoneJoue, Parcelle.Couleur.JAUNE) && couleurParcellePresente(zoneJoue, Parcelle.Couleur.ROSE);
    }

    //renvoi vrai si une parcelle est irriguee et sans enclos
    private boolean couleurParcellePresente(ArrayList<Parcelle> zoneJoue, Parcelle.Couleur couleur) {
        for (Parcelle p : zoneJoue) {
            if (p.getCouleur() == couleur && p.isIrriguee() && p.getEffet() != Parcelle.Effet.ENCLOS) {
                return true;
            }
        }
        return false;
    }

    private void faireAction() {
        Parcelle coupJoue;
        if (iService.feuilleJoueurGetActionChoisie() == 0) {
            // Bot pioche 3 parcelles, en choisit une aléatoirement et choisit une position aléatoire parmi la liste d'adjacences
            coupJoue = choisirParcelle(iService.getZoneJouee(), iService.piocher());
            //positionsCoupJoue
            coupJoue.setCoord(choisirPosition(iService.getListeZonesPosables()));
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
            valeurDeplacementPanda(iService.pandaGetDeplacementsPossible(), hashZoneJouee(iService.getZoneJouee()));
            deplacementPanda(posCartePanda());
        } else if (iService.feuilleJoueurGetActionChoisie() == 3) {
            HashMap<Coordonnees, Parcelle> zoneJouee = hashZoneJouee(iService.getZoneJouee());
            //Bot déplace le jardinier
            valeurDeplacementJardinier(iService.jardinierGetDeplacementsPossible(), zoneJouee);
            deplacementJardinier(posCartePanda(), zoneJouee);
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

    private void valeurDeplacementPanda(ArrayList<Coordonnees> zoneDeDeplacement, HashMap<Coordonnees, Parcelle> zoneJouee) {
        for (MainJoueur m : mainObjectifValeur) {
            ArrayList<Parcelle.Couleur> couleursPossible = choixCouleur(m);

            ArrayList<Coordonnees> coordonneesPossible = deplacementPandaPossible(zoneDeDeplacement, couleursPossible, zoneJouee);

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

    private void valeurDeplacementJardinier(ArrayList<Coordonnees> zoneDeDeplacement, HashMap<Coordonnees, Parcelle> zoneJouee) {
        for (MainJoueur m : mainObjectifValeur) {
            ArrayList<Parcelle.Couleur> couleursPossible = choixCouleur(m);

            ArrayList<Coordonnees> coordonneesPossible = deplacementJardinierPossibe(zoneDeDeplacement, couleursPossible, zoneJouee);

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

    private Parcelle choisirParcelle(ArrayList<Parcelle> zoneJoue, ArrayList<Parcelle> troisParcelles) {
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

    private void deplacementPanda(int j) {
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
                faireAction();
            } else if (iService.feuilleJoueurGetActionChoisie() != 3 && iService.feuilleJoueurGetNbAction() == 1) {
                iService.feuilleJoueurSetActionChoisie(3);
                faireAction();
            }
        } else {
            //sinon on essaye le jardinier
            iService.feuilleJoueurSetActionChoisie(3);
            faireAction();
        }
    }

    private void deplacementJardinier(int j, HashMap<Coordonnees, Parcelle> zoneJouee) {
        //si un deplacement interessant
        if (j != 15 && mainObjectifValeur.get(j).getCout() != 0) {
            try {
                iService.deplacerJardinier(mainObjectifValeur.get(j).getCoordonneesJardinierPossible().get(0));
                LOGGER.info(nomBot + " a déplacé le jardinier a la coordonnée : " + iService.jardinierGetCoordonnees());
            } catch (TricheException e) {
                aucunCoupInterressant(iService.jardinierGetDeplacementsPossible(), iService.jardinierGetCoordonnees(), zoneJouee);
            }
        } else {
            //sinon
            aucunCoupInterressant(iService.jardinierGetDeplacementsPossible(), iService.jardinierGetCoordonnees(), zoneJouee);
        }
    }

    private void aucunCoupInterressant(ArrayList<Coordonnees> jardinierDeplacementsPossible, Coordonnees jardinierCoordonnees, HashMap<Coordonnees, Parcelle> zoneJouee) {
        verifObjectif();
        if (mainObjectif.size() < TAILLE_MAX_MAIN_OBJECTIF && iService.feuilleJoueurGetActionChoisie() != 1 && !iService.piochePandaIsEmpty() && iService.feuilleJoueurGetNbAction() == 1) {
            iService.feuilleJoueurSetActionChoisie(1);
            faireAction();
        } else {
            try {
                Coordonnees c = deplacementAvancePanda(iService.pandaGetDeplacementsPossible(), iService.pandaGetCoordonnees(), zoneJouee);
                if (c != null) {
                    iService.deplacerPanda(c);
                    LOGGER.info(nomBot + " a déplacé le panda a la coordonnée (avancee): " + iService.pandaGetCoordonnees());
                } else {
                    c = deplacementAvanceJardinier(jardinierDeplacementsPossible, jardinierCoordonnees, zoneJouee);
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
                Coordonnees c = deplacementAvanceJardinier(jardinierDeplacementsPossible, jardinierCoordonnees, zoneJouee);
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

    private Coordonnees deplacementAvancePanda(ArrayList<Coordonnees> pandaDeplacementsPossible, Coordonnees pandaCoordonnees, HashMap<Coordonnees, Parcelle> zoneJouee) {
        Coordonnees c = null;
        valeurDeplacementPanda(listZoneJouee(iService.getZoneJouee()), zoneJouee);
        int posCarte = posCartePanda();
        ArrayList<Coordonnees> coordonnees;
        if (posCarte != 15 && mainObjectifValeur.get(posCarte).getCout() != 0) {
            ArrayList<Coordonnees> cooParcelle = new ArrayList<>();
            //coordonnes ou panda peut aller (sur tout le terrain)
            coordonnees = mainObjectifValeur.get(posCarte).getCoordonneesPandaPossible();
            for (Coordonnees co : coordonnees) {
                //on récupère  les coordonnes qui nous permettent d'aller sur celle voulu
                co.deplacementPossible(hashZoneJouee(iService.getZoneJouee()), cooParcelle);
            }
            for (Coordonnees co : pandaDeplacementsPossible) {
                if (cooParcelle.contains(co)) {
                    //si le panda peut si rendre
                    c = co;
                    break;
                }
            }
        } else if (!pandaCoordonnees.equals(new Coordonnees(0, 0, 0))) {
            c = procheDuCentre(pandaDeplacementsPossible, pandaCoordonnees);
        }
        return c;
    }

    private Coordonnees deplacementAvanceJardinier(ArrayList<Coordonnees> jardinierDeplacementsPossible, Coordonnees jardinierCoordonnees, HashMap<Coordonnees, Parcelle> zoneJouee) {
        Coordonnees c = null;
        valeurDeplacementJardinier(listZoneJouee(iService.getZoneJouee()), zoneJouee);
        int posCarte = posCartePanda();
        ArrayList<Coordonnees> coordonnees;
        if (posCarte != 15 && mainObjectifValeur.get(posCarte).getCout() != 0) {
            ArrayList<Coordonnees> cooParcelle = new ArrayList<>();
            coordonnees = mainObjectifValeur.get(posCarte).getCoordonneesJardinierPossible();
            for (Coordonnees co : coordonnees) {
                //on récupère  les coordonnes qui nous permettent d'aller sur celle voulu
                co.deplacementPossible(zoneJouee, cooParcelle);
            }
            for (Coordonnees co : jardinierDeplacementsPossible) {
                if (cooParcelle.contains(co)) {
                    //si le jardinier peut si rendre
                    c = co;
                    break;
                }
            }
        } else if (!jardinierCoordonnees.equals(new Coordonnees(0, 0, 0))) {
            c = procheDuCentre(jardinierDeplacementsPossible, jardinierCoordonnees);
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

    private ArrayList<Coordonnees> deplacementPandaPossible(ArrayList<Coordonnees> deplacementsPossibles, ArrayList<Parcelle.Couleur> couleurPossible, HashMap<Coordonnees, Parcelle> zoneJouee) {
        ArrayList<Coordonnees> coordonneesPossible = new ArrayList<>();
        for (Parcelle.Couleur couleur : couleurPossible) {
            for (Coordonnees c : deplacementsPossibles) {
                Parcelle p = zoneJouee.get(c);
                if (p.getCouleur() == couleur && p.getEffet() != Parcelle.Effet.ENCLOS && p.getBambou() != 0) {
                    coordonneesPossible.add(c);
                }
            }
        }
        return coordonneesPossible;
    }

    private ArrayList<Coordonnees> deplacementJardinierPossibe(ArrayList<Coordonnees> deplacementsPossibles, ArrayList<Parcelle.Couleur> couleurPossible, HashMap<Coordonnees, Parcelle> zoneJouee) {
        ArrayList<Coordonnees> coordonneesPossible = new ArrayList<>();
        for (Parcelle.Couleur couleur : couleurPossible) {
            for (Coordonnees c : deplacementsPossibles) {
                Parcelle p = zoneJouee.get(c);
                if (p.getCouleur() == couleur && p.getEffet() != Parcelle.Effet.ENCLOS && p.getBambou() <= 2 && p.isIrriguee()) {
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

    private ArrayList<Coordonnees> listZoneJouee(ArrayList<Parcelle> ZoneJouee) {
        ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        for (int i = 0; i < ZoneJouee.size(); i++) {
            coordonnees.add(ZoneJouee.get(i).getCoord());
        }
        return coordonnees;
    }

    private HashMap<Coordonnees, Parcelle> hashZoneJouee(ArrayList<Parcelle> arrayListParcelle) {
        HashMap<Coordonnees, Parcelle> zoneJouee = new HashMap<>();
        for (Parcelle p : arrayListParcelle) {
            zoneJouee.put(p.getCoord(), p);
        }
        return zoneJouee;
    }

    public ArrayList<CartesObjectifs> getMainObjectif() {
        return mainObjectif;
    }


    public void setMainObjectif(ArrayList<CartesObjectifs> mainObjectif) {
        this.mainObjectif = mainObjectif;
    }

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
