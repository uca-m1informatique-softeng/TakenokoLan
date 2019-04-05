package commun.pioches;

import commun.ressources.FeuilleJoueur;
import commun.triche.TricheException;

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

    public void piocherUnObjectif(FeuilleJoueur feuilleJoueur, int i) throws TricheException {
        if (feuilleJoueur.getPrecedant() != 1) {
            //Bot pioche une carte objectif Parcelle
            if (i == 0 && !laPiocheObjectifParcelles.getPioche().isEmpty()) {
                feuilleJoueur.decNbACtion();
                feuilleJoueur.setPrecedant(1);
                feuilleJoueur.getMainObjectif().add(laPiocheObjectifParcelles.piocher());
                LOGGER.info(feuilleJoueur.getName() + " obtient l'objectif de parcelle : " + feuilleJoueur.getMainObjectif().get(feuilleJoueur.getMainObjectif().size() - 1));
            }
            //Bot pioche une carte objectif Jardiner
            if (i == 1 && !laPiocheObjectifsJardinier.getPioche().isEmpty()) {
                feuilleJoueur.decNbACtion();
                feuilleJoueur.setPrecedant(1);
                feuilleJoueur.getMainObjectif().add(laPiocheObjectifsJardinier.piocher());
                LOGGER.info(feuilleJoueur.getName() + " obtient l'objectif de jardinier : " + feuilleJoueur.getMainObjectif().get(feuilleJoueur.getMainObjectif().size() - 1));
            }
            //Bot pioche une carte objectif Panda
            if (i == 2 && !laPiocheObjectifsPanda.getPioche().isEmpty()) {
                feuilleJoueur.decNbACtion();
                feuilleJoueur.setPrecedant(1);
                feuilleJoueur.getMainObjectif().add(laPiocheObjectifsPanda.piocher());
                LOGGER.info(feuilleJoueur.getName() + " obtient l'objectif de panda : " + feuilleJoueur.getMainObjectif().get(feuilleJoueur.getMainObjectif().size() - 1));
            }
        } else {
            throw new TricheException("impossible de piocher 2 fois");
        }
    }
}
