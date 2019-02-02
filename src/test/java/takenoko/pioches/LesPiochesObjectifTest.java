package takenoko.pioches;

import org.junit.Assert;
import org.junit.Test;
import takenoko.ia.IA;
import takenoko.ia.IAPanda;
import takenoko.ressources.CarteObjectifJardinier;
import takenoko.ressources.CarteObjectifPanda;
import takenoko.ressources.CarteObjectifParcelle;
import takenoko.ressources.CartesObjectifs;
import takenoko.utilitaires.TricheException;

public class LesPiochesObjectifTest {

    @Test
    public void LesPiochesObjectif() {
        try {
            IAPanda IAPanda = new IAPanda();
            IAPanda.setNomBot("Joe");
            LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();

            //Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifParcelles());
            Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifsPanda());
            //Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifsJardinier());

            lesPiochesObjectif.piocherUnObjectif(IAPanda, 0);
            IAPanda.getFeuilleJoueur().initNbAction();
            Assert.assertTrue(verifParcelle(IAPanda.getMainObjectif().get(0)));
            lesPiochesObjectif.piocherUnObjectif(IAPanda, 1);
            IAPanda.getFeuilleJoueur().initNbAction();
            Assert.assertTrue(verifJardinier(IAPanda.getMainObjectif().get(1)));
            lesPiochesObjectif.piocherUnObjectif(IAPanda, 2);
            IAPanda.getFeuilleJoueur().initNbAction();
            Assert.assertTrue(verifPanda(IAPanda.getMainObjectif().get(2)));

            lesPiochesObjectif.getLaPiocheObjectifsPanda().getPioche().clear();
            //lesPiochesObjectif.getLaPiocheObjectifsJardinier().getPioche().clear();
            //lesPiochesObjectif.getLaPiocheObjectifParcelles().getPioche().clear();
            IAPanda.getMainObjectif().clear();

            lesPiochesObjectif.piocherUnObjectif(IAPanda, 0);
            IAPanda.getFeuilleJoueur().initNbAction();
            Assert.assertEquals(1, IAPanda.getMainObjectif().size());
            lesPiochesObjectif.piocherUnObjectif(IAPanda, 1);
            IAPanda.getFeuilleJoueur().initNbAction();
            Assert.assertEquals(2, IAPanda.getMainObjectif().size());
            lesPiochesObjectif.piocherUnObjectif(IAPanda, 2);
            IAPanda.getFeuilleJoueur().initNbAction();
            Assert.assertEquals(2, IAPanda.getMainObjectif().size());
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
