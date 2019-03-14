package takenoko.ressources;

import org.junit.Assert;
import org.junit.Test;

public class FeuilleJoueurTest {

    @Test
    public void feuilleJoueurTest() {
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("");

        Assert.assertEquals(2, feuilleJoueur.getNbAction());
        Assert.assertEquals(5, feuilleJoueur.getActionChoisie());

        Assert.assertEquals(0, feuilleJoueur.getNbBambouJaune());
        Assert.assertEquals(0, feuilleJoueur.getNbBambouVert());
        Assert.assertEquals(0, feuilleJoueur.getNbBambouRose());

        feuilleJoueur.decNbACtion();
        Assert.assertEquals(1, feuilleJoueur.getNbAction());

        feuilleJoueur.setActionChoisie(2);
        Assert.assertEquals(2, feuilleJoueur.getActionChoisie());

        feuilleJoueur.initNbAction();
        Assert.assertEquals(2, feuilleJoueur.getNbAction());
        Assert.assertEquals(5, feuilleJoueur.getActionChoisie());

        feuilleJoueur.incBambouJaune();
        feuilleJoueur.incBambouRose();
        feuilleJoueur.incBambouVert();

        Assert.assertEquals(1, feuilleJoueur.getNbBambouJaune());
        Assert.assertEquals(1, feuilleJoueur.getNbBambouVert());
        Assert.assertEquals(1, feuilleJoueur.getNbBambouRose());

        feuilleJoueur.decBambouJaune();
        feuilleJoueur.decBambouRose();
        feuilleJoueur.decBambouVert();

        Assert.assertEquals(0, feuilleJoueur.getNbBambouJaune());
        Assert.assertEquals(0, feuilleJoueur.getNbBambouVert());
        Assert.assertEquals(0, feuilleJoueur.getNbBambouRose());

    }
}
