package takenoko.utilitaires;

import org.junit.Assert;
import org.junit.Test;
import takenoko.ressources.Coordonnees;

public class CoordonneesTest {

    private Coordonnees coordonnees1;
    private Coordonnees coordonnees2;
    private Coordonnees coordonnees3;
    private Coordonnees coordonnees4;

    @Test
    public void coordonneesTest() {
        this.coordonnees2 = new Coordonnees(0, 2, 1);
        this.coordonnees3 = new Coordonnees(0, 2, 1);
        this.coordonnees1 = new Coordonnees(0, 2, 0);
        this.coordonnees4 = null;

        Assert.assertEquals(false, coordonnees2.equals(new String("")));
        Assert.assertEquals(true, coordonnees3.equals(coordonnees2));
        Assert.assertEquals(false, coordonnees3.equals(coordonnees4));
        Assert.assertEquals(false, coordonnees1.equals(coordonnees3));
    }

}
