package takenoko.ia;

import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.CartesObjectifs;
import takenoko.ressources.FeuilleJoueur;
import takenoko.ressources.Parcelle;
import takenoko.ressources.Coordonnees;
import takenoko.service.impl.ClientService;

import java.util.ArrayList;
import java.util.Map;

public interface IA {
    ArrayList<CartesObjectifs> getMainObjectif();

    enum Type {RANDOM, PANDA}

    void setMainObjectif(ArrayList<CartesObjectifs> mainObjectif);

    FeuilleJoueur getFeuilleJoueur();

    void joue(LaPiocheParcelle piocheParcelle, Terrain terrain, LesPiochesObjectif lesPiochesObjectif, Jardinier jardinier, Panda panda);

    String getNomBot();

    void setNomBot(String nomBot);

    static IA newIA(Type type) {
        if (type == Type.RANDOM) {
            return new IARandom();
        } else {
            return new IAPanda();
        }
    }

    static void verifObjectifAccompli(Terrain terrain, IA bot) {
        ArrayList<CartesObjectifs> mainObjectif = bot.getMainObjectif();
        for (Map.Entry<Coordonnees, Parcelle> entry : terrain.getZoneJouee().entrySet()) {
            Parcelle valeur = entry.getValue();
            for (int i = 0; i < mainObjectif.size(); i++) {
                mainObjectif.get(i).verifObjectif(terrain, valeur, bot.getFeuilleJoueur());
            }
            bot.setMainObjectif(mainObjectif);
        }
    }

    void setiService(ClientService iService);
}
