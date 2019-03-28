package serveur.utilitaires;

import org.junit.Assert;
import org.junit.Test;
import takenoko.ia.IA;
import joueur.ia.IAPanda;

public class StatistiqueJoueurTest {
    StatistiqueJoueur statistiqueJoueur = new StatistiqueJoueur(IA.newIA(IA.Type.PANDA), 0, 0, 0, "");

    @Test
    public void getIa() {
        Assert.assertTrue(typeIA(statistiqueJoueur.getIa()));
    }

    private boolean typeIA(IA ia) {
        if (ia instanceof IAPanda) {
            return true;
        } else {
            return false;
        }
    }

    @Test
    public void getNbVictoire() {
        Assert.assertEquals(0, statistiqueJoueur.getNbVictoire());
    }

    @Test
    public void getNbPointsTotal() {
        Assert.assertEquals(0, statistiqueJoueur.getNbPointsTotal());
    }

    @Test
    public void getNbToursTotal() {
        Assert.assertEquals(0, statistiqueJoueur.getNbToursTotal());
    }

    @Test
    public void incrNVictoire() {
        statistiqueJoueur.incrNVictoire();
        Assert.assertEquals(1, statistiqueJoueur.getNbVictoire());
    }

    @Test
    public void incrNbPointsTotal() {
        statistiqueJoueur.incrNbPointsTotal(1);
        Assert.assertEquals(1, statistiqueJoueur.getNbPointsTotal());
    }

    @Test
    public void setIa() {
        statistiqueJoueur.setIa(IA.newIA(IA.Type.RANDOM));
        Assert.assertFalse(typeIA(statistiqueJoueur.getIa()));
    }

    @Test
    public void incrNbToursTotal() {
        statistiqueJoueur.incrNbToursTotal(1);
        Assert.assertEquals(1, statistiqueJoueur.getNbToursTotal());
    }
}