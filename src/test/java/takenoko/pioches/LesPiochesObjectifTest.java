package takenoko.pioches;

import org.junit.Assert;
import org.junit.Test;
import takenoko.configuration.Takenoko;
import takenoko.ia.IA;
import takenoko.ia.IAPanda;
import takenoko.ia.IARandom;
import takenoko.ressources.CarteObjectifJardinier;
import takenoko.ressources.CarteObjectifPanda;
import takenoko.ressources.CarteObjectifParcelle;
import takenoko.ressources.CartesObjectifs;
import takenoko.utilitaires.StatistiqueJoueur;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;

public class LesPiochesObjectifTest {

    @Test
    public void LesPiochesObjectif() {
        try {

            IARandom iaRandom=new IARandom();
            iaRandom.setNomBot("Joe");
            LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();

            //Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifParcelles());
            Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifsPanda());
            //Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifsJardinier());

            lesPiochesObjectif.piocherUnObjectif(iaRandom.getFeuilleJoueur(), 0);
            iaRandom.getFeuilleJoueur().initNbAction();
            Assert.assertTrue(verifParcelle(iaRandom.getFeuilleJoueur().getMainObjectif().get(0)));
            lesPiochesObjectif.piocherUnObjectif(iaRandom.getFeuilleJoueur(), 1);
            iaRandom.getFeuilleJoueur().initNbAction();
            Assert.assertTrue(verifJardinier(iaRandom.getFeuilleJoueur().getMainObjectif().get(1)));
            lesPiochesObjectif.piocherUnObjectif(iaRandom.getFeuilleJoueur(), 2);
            iaRandom.getFeuilleJoueur().initNbAction();
            Assert.assertTrue(verifPanda(iaRandom.getFeuilleJoueur().getMainObjectif().get(2)));

            lesPiochesObjectif.getLaPiocheObjectifsPanda().getPioche().clear();
            //lesPiochesObjectif.getLaPiocheObjectifsJardinier().getPioche().clear();
            //lesPiochesObjectif.getLaPiocheObjectifParcelles().getPioche().clear();
            iaRandom.getFeuilleJoueur().getMainObjectif().clear();

            lesPiochesObjectif.piocherUnObjectif(iaRandom.getFeuilleJoueur(), 0);
            iaRandom.getFeuilleJoueur().initNbAction();
            Assert.assertEquals(1, iaRandom.getFeuilleJoueur().getMainObjectif().size());
            lesPiochesObjectif.piocherUnObjectif(iaRandom.getFeuilleJoueur(), 1);
            iaRandom.getFeuilleJoueur().initNbAction();
            Assert.assertEquals(2, iaRandom.getFeuilleJoueur().getMainObjectif().size());
            lesPiochesObjectif.piocherUnObjectif(iaRandom.getFeuilleJoueur(), 2);
            iaRandom.getFeuilleJoueur().initNbAction();
            Assert.assertEquals(2, iaRandom.getFeuilleJoueur().getMainObjectif().size());
        } catch (TricheException e) {
            e.printStackTrace();
        }
    }

    public boolean verifPanda(CartesObjectifs c) {
        if (c instanceof CarteObjectifPanda) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifJardinier(CartesObjectifs c) {
        if (c instanceof CarteObjectifJardinier) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifParcelle(CartesObjectifs c) {
        if (c instanceof CarteObjectifParcelle) {
            return true;
        } else {
            return false;
        }
    }
}
