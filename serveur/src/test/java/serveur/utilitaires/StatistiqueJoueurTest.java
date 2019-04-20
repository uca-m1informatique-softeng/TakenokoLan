package serveur.utilitaires;

import org.junit.Assert;
import org.junit.Test;

public class StatistiqueJoueurTest {
    StatistiqueJoueur statistiqueJoueur = new StatistiqueJoueur(1, 0, 0, 0, "");

    @Test
    public void setRefJoueur() {
        statistiqueJoueur.setRefJoueur(1);
        Assert.assertEquals(1, statistiqueJoueur.getRefJoueur());
    }

    @Test
    public void getRefJoueur() {
        Assert.assertEquals(1, statistiqueJoueur.getRefJoueur());
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
    public void incrNbToursTotal() {
        statistiqueJoueur.incrNbToursTotal(1);
        Assert.assertEquals(1, statistiqueJoueur.getNbToursTotal());
    }
}