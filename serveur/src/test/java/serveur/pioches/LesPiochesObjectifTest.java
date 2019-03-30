package serveur.pioches;

import commun.ressources.CarteObjectifJardinier;
import commun.ressources.CarteObjectifPanda;
import commun.ressources.CarteObjectifParcelle;
import commun.ressources.CartesObjectifs;
import commun.triche.TricheException;
import org.junit.Assert;
import org.junit.Test;
import serveur.iaForTest.IARandom;

public class LesPiochesObjectifTest {

    @Test
    public void LesPiochesObjectif() throws TricheException {
        IARandom iaRandom = new IARandom();
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
