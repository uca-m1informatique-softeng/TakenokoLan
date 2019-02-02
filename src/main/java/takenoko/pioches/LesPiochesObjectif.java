package takenoko.pioches;

import takenoko.ia.IA;
import takenoko.utilitaires.TricheException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LesPiochesObjectif {

    public static final Logger LOGGER = Logger.getLogger(LesPiochesObjectif.class.getCanonicalName());
    private LaPiocheObjectifsJardinier laPiocheObjectifsJardinier;
    private LaPiocheObjectifsParcelles laPiocheObjectifParcelles;
    private LaPiocheObjectifsPanda laPiocheObjectifsPanda;

    public LesPiochesObjectif() {
        LOGGER.setLevel(Level.OFF);
        laPiocheObjectifParcelles = new LaPiocheObjectifsParcelles();
        laPiocheObjectifsJardinier = new LaPiocheObjectifsJardinier();
        laPiocheObjectifsPanda = new LaPiocheObjectifsPanda();
    }

    public LaPiocheObjectifsJardinier getLaPiocheObjectifsJardinier() {
        return laPiocheObjectifsJardinier;
    }

    public LaPiocheObjectifsParcelles getLaPiocheObjectifParcelles() {
        return laPiocheObjectifParcelles;
    }

    public LaPiocheObjectifsPanda getLaPiocheObjectifsPanda() {
        return laPiocheObjectifsPanda;
    }

    public boolean isEmptyPiocheObjectifsPanda() {
        if (laPiocheObjectifsPanda.getPioche().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void piocherUnObjectif(IA bot, int i) throws TricheException {
        if (bot.getFeuilleJoueur().getPrecedant() != 1) {
            //Bot pioche une carte objectif Parcelle
            if (i == 0 && !laPiocheObjectifParcelles.getPioche().isEmpty()) {
                bot.getFeuilleJoueur().decNbACtion();
                bot.getFeuilleJoueur().setPrecedant(1);
                bot.getMainObjectif().add(laPiocheObjectifParcelles.piocher());
                LOGGER.info(bot.getNomBot() + " obtient l'objectif de parcelle : " + bot.getMainObjectif().get(bot.getMainObjectif().size() - 1));
            }
            //Bot pioche une carte objectif Jardiner
            if (i == 1 && !laPiocheObjectifsJardinier.getPioche().isEmpty()) {
                bot.getFeuilleJoueur().decNbACtion();
                bot.getFeuilleJoueur().setPrecedant(1);
                bot.getMainObjectif().add(laPiocheObjectifsJardinier.piocher());
                LOGGER.info(bot.getNomBot() + " obtient l'objectif de jardinier : " + bot.getMainObjectif().get(bot.getMainObjectif().size() - 1));
            }
            //Bot pioche une carte objectif Panda
            if (i == 2 && !laPiocheObjectifsPanda.getPioche().isEmpty()) {
                bot.getFeuilleJoueur().decNbACtion();
                bot.getFeuilleJoueur().setPrecedant(1);
                bot.getMainObjectif().add(laPiocheObjectifsPanda.piocher());
                LOGGER.info(bot.getNomBot() + " obtient l'objectif de panda : " + bot.getMainObjectif().get(bot.getMainObjectif().size() - 1));
            }
        } else {
            throw new TricheException("impossible de piocher 2 fois");
        }
    }
}
