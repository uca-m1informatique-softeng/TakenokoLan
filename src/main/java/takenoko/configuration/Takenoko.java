package takenoko.configuration;


import org.springframework.context.annotation.Configuration;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.ia.IA;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.utilitaires.StatistiqueJoueur;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class Takenoko {

    public static final Logger LOGGER = Logger.getLogger(takenoko.configuration.Takenoko.class.getCanonicalName());
    private int nbNull = 0;
    private ArrayList<StatistiqueJoueur> listPlayer = new ArrayList<>();

    private Terrain terrain;
    private Panda panda;
    private Jardinier jardinier;
    private LaPiocheParcelle laPiocheParcelle;
    private LesPiochesObjectif lesPiochesObjectif;

    public String nbPartie(int nbPartie, int nbBotBob, int nbBotJoe) {
        LOGGER.info(nbPartie + " parties avec " + nbBotBob + " IA random et " + nbBotJoe + " IA panda");
        //init bot Bob
        for (int j = 0; j < nbBotBob; j++) {
            listPlayer.add(new StatistiqueJoueur(null, 0, 0, 0, "IA random" + (j + 1)));
        }
        //init bot Joe
        for (int j = nbBotBob; j < (nbBotJoe + nbBotBob); j++) {
            listPlayer.add(new StatistiqueJoueur(null, 0, 0, 0, "IA panda" + (j + 1)));
        }
        //Boucle sur le nombre de partie
        for (int i = 0; i < nbPartie; i++) {
            //on reset les bots a chaque partie
            for (int j = 0; j < nbBotBob; j++) {
                listPlayer.get(j).setIa(IA.newIA(IA.Type.RANDOM));
                listPlayer.get(j).getIa().setNomBot("IA random" + (j + 1));
            }
            for (int j = nbBotBob; j < (nbBotJoe + nbBotBob); j++) {
                listPlayer.get(j).setIa(IA.newIA(IA.Type.PANDA));
                listPlayer.get(j).getIa().setNomBot("IA panda" + (j + 1));
            }
            //Collections.shuffle(listPlayer);
            //lance une partie
            partie(listPlayer);
        }
        //affichage final de toutes les statistiques
        afficherVainqueur(listPlayer, nbPartie);

        return "hello";
    }

    public void lancerParti(ArrayList<StatistiqueJoueur> listPlayer) {
        this.listPlayer = listPlayer;
        LOGGER.info("Partie lancer avec " + listPlayer.size() + " joueur(s)");
        partie(listPlayer);
        afficherVainqueur(listPlayer, 1);
    }

    private void partie(ArrayList<StatistiqueJoueur> listPlayer) {

        // initialisations
        initPartie();

        boolean finPartie = false;
        boolean jeuInfini = false;

        int nbTour = 0;

        int memoirePoints[] = new int[listPlayer.size()];
        for (int j = 0; j < listPlayer.size(); j++) {
            memoirePoints[j] = -1;
        }
        int nbTourSansObjectif = 0;
        // déroulement de la partie
        while (!finPartie) {
            //LOGGER.info("Tour numéro " + (nbTour + 1));
            for (StatistiqueJoueur c : listPlayer) {
                c.getIa().joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
            }
            for (int j = 0; j < listPlayer.size(); j++) {
                if (listPlayer.get(j).getFeuilleJoueur().getNbObjectifsValide() >= 9 && j == 0) {
                    listPlayer.get(j).getFeuilleJoueur().setPointsBot(listPlayer.get(j).getFeuilleJoueur().getPointsBot() + 2);
                    finPartie = true;
                }
                if (listPlayer.get(j).getFeuilleJoueur().getNbObjectifsValide() >= 9 && j != 0) {
                    listPlayer.get(j).getFeuilleJoueur().setPointsBot(listPlayer.get(j).getFeuilleJoueur().getPointsBot() + 2);
                    finPartie = true;
                    //fait joué tout les autres joueurs sauf lui
                    for (StatistiqueJoueur c : listPlayer) {
                        if (listPlayer.indexOf(c) != j) {
                            c.getIa().joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
                        }
                    }
                }
            }

            for (int j = 0; j < listPlayer.size(); j++) {
                if (memoirePoints[j] == listPlayer.get(j).getFeuilleJoueur().getPointsBot()) {
                    jeuInfini = true;
                } else {
                    jeuInfini = false;
                }
            }
            if (jeuInfini) {
                nbTourSansObjectif++;
                if (nbTourSansObjectif >= 30) {
                    finPartie = true;
                }
            } else {
                nbTourSansObjectif = 0;
                for (int j = 0; j < listPlayer.size(); j++) {
                    memoirePoints[j] = listPlayer.get(j).getFeuilleJoueur().getPointsBot();
                }
            }
            nbTour++;
        }
        pointVictoire(listPlayer, nbTour);
    }

    private void afficherVainqueur(ArrayList<StatistiqueJoueur> listPlayer, int nbPartie) {
        float pourcentageNull = (float) (1.0 * (100 * nbNull) / nbPartie);
        float pourcentageGagner;
        float pourcentagePardu;
        int nbTour;
        for (StatistiqueJoueur j : listPlayer) {
            pourcentageGagner = (float) (1.0 * (100 * j.getNbVictoire()) / nbPartie);
            pourcentagePardu = (float) (1.0 * (100 * (nbPartie - j.getNbVictoire() - nbNull)) / nbPartie);
            nbTour = 0;
            if (j.getNbVictoire() != 0) {
                nbTour = j.getNbToursTotal() / j.getNbVictoire();
            }
            LOGGER.info("---->" + j.getIa().getNomBot() + " gagne " + pourcentageGagner + "% | perd " + pourcentagePardu + "% | null " + pourcentageNull + "% avec un ratio de " + (j.getNbPointsTotal() / nbPartie) + " points par partie");
            LOGGER.info("en " + nbTour + " tour par victoire");
            LOGGER.info("nb tour MIN pour une victoire " + j.getNbToursMIN());
            LOGGER.info("nb tour MAX pour une victoire " + j.getNbToursMAX());
        }
    }

    private void pointVictoire(ArrayList<StatistiqueJoueur> listPlayer, int nbTour) {
        int maxPoints = 0;
        for (int i = 0; i < listPlayer.size(); i++) {
            if (maxPoints < listPlayer.get(i).getFeuilleJoueur().getPointsBot()) {
                maxPoints = listPlayer.get(i).getFeuilleJoueur().getPointsBot();
            }
        }
        ArrayList<StatistiqueJoueur> aGagner = new ArrayList<>();
        for (int i = 0; i < listPlayer.size(); i++) {
            if (listPlayer.get(i).getFeuilleJoueur().getPointsBot() == maxPoints) {
                aGagner.add(listPlayer.get(i));
            }
        }
        if (aGagner.size() == 1) {
            aGagner.get(0).incrNVictoire();
            aGagner.get(0).incrNbToursTotal(nbTour);
        } else {
            nbNull++;
        }
        for (StatistiqueJoueur s : listPlayer) {
            s.incrNbPointsTotal(s.getFeuilleJoueur().getPointsBot());
        }
    }

    public void initPartie() {
        terrain = new Terrain();
        panda = new Panda(terrain);
        jardinier = new Jardinier(terrain);
        laPiocheParcelle = new LaPiocheParcelle();
        lesPiochesObjectif = new LesPiochesObjectif();
    }

    public Takenoko takenoko() {
        LOGGER.setLevel(Level.ALL);
        nbPartie(1, 0, 1);
        return new Takenoko();
    }

    public LaPiocheParcelle getLaPiocheParcelle() {
        return laPiocheParcelle;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Panda getPanda() {
        return panda;
    }

    public Jardinier getJardinier() {
        return jardinier;
    }

    public LesPiochesObjectif getLesPiochesObjectif() {
        return lesPiochesObjectif;
    }

    public ArrayList<StatistiqueJoueur> getListPlayer() {
        return listPlayer;
    }

    public void setListPlayer(ArrayList<StatistiqueJoueur> listPlayer) {
        this.listPlayer = listPlayer;
    }
}