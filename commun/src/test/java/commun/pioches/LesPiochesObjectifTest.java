package commun.pioches;

import commun.ressources.*;
import commun.triche.TricheException;
import org.junit.Assert;
import org.junit.Test;

public class LesPiochesObjectifTest {

    @Test
    public void LesPiochesObjectif() throws TricheException {
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("Joe");
        LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();

        //Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifParcelles());
        Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifsPanda());
        //Assert.assertNotEquals(null, lesPiochesObjectif.getLaPiocheObjectifsJardinier());

        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 0);
        feuilleJoueur.initNbAction();
        Assert.assertTrue(verifParcelle(feuilleJoueur.getMainObjectif().get(0)));
        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 1);
        feuilleJoueur.initNbAction();
        Assert.assertTrue(verifJardinier(feuilleJoueur.getMainObjectif().get(1)));
        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 2);
        feuilleJoueur.initNbAction();
        Assert.assertTrue(verifPanda(feuilleJoueur.getMainObjectif().get(2)));

        lesPiochesObjectif.getLaPiocheObjectifsPanda().getPioche().clear();
        //lesPiochesObjectif.getLaPiocheObjectifsJardinier().getPioche().clear();
        //lesPiochesObjectif.getLaPiocheObjectifParcelles().getPioche().clear();
        feuilleJoueur.getMainObjectif().clear();

        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 0);
        feuilleJoueur.initNbAction();
        Assert.assertEquals(1, feuilleJoueur.getMainObjectif().size());
        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 1);
        feuilleJoueur.initNbAction();
        Assert.assertEquals(2, feuilleJoueur.getMainObjectif().size());
        lesPiochesObjectif.piocherUnObjectif(feuilleJoueur, 2);
        feuilleJoueur.initNbAction();
        Assert.assertEquals(2, feuilleJoueur.getMainObjectif().size());

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
