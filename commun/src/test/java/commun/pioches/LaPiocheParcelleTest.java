package commun.pioches;

import commun.pioches.LaPiocheParcelle;
import org.junit.Assert;
import org.junit.Test;
import commun.ressources.Parcelle;

import java.util.ArrayList;

public class LaPiocheParcelleTest {

    @Test
    public void laPiocheParcelleTest() {
        int NB_PARCELLE_JAUNE = 9;
        int NB_PARCELLE_ROSE = 7;
        int NB_PARCELLE_VERTE = 11;
        LaPiocheParcelle laPiocheParcelle = new LaPiocheParcelle();
        int NB_PARCELLE_TOTAL = NB_PARCELLE_JAUNE + NB_PARCELLE_ROSE + NB_PARCELLE_VERTE;
        int j = 0;
        int r = 0;
        int v = 0;
        for (int i = 0; i < 27; i++) {
            if (laPiocheParcelle.getPioche().get(i).getCouleur() == Parcelle.Couleur.JAUNE) j++;
            if (laPiocheParcelle.getPioche().get(i).getCouleur() == Parcelle.Couleur.ROSE) r++;
            if (laPiocheParcelle.getPioche().get(i).getCouleur() == Parcelle.Couleur.VERTE) v++;
        }
        Assert.assertEquals(NB_PARCELLE_JAUNE, j);
        Assert.assertEquals(NB_PARCELLE_ROSE, r);
        Assert.assertEquals(NB_PARCELLE_VERTE, v);
        Assert.assertEquals(NB_PARCELLE_TOTAL, laPiocheParcelle.getPioche().size());

        ArrayList<Parcelle> main;

        for (int i = NB_PARCELLE_JAUNE + NB_PARCELLE_ROSE + NB_PARCELLE_VERTE - 1; i > 0; i--) {
            main = laPiocheParcelle.piocherParcelle();
            if (i >= 2) {
                Assert.assertEquals(laPiocheParcelle.NB_CARTE_A_PIOCHER, main.size());
                main.remove(1);
                laPiocheParcelle.reposeSousLaPioche(main);
                NB_PARCELLE_TOTAL--;
                Assert.assertEquals(NB_PARCELLE_TOTAL, laPiocheParcelle.getPioche().size());

            } else {
                Assert.assertEquals(i + 1, main.size());
                main.remove(1);
                laPiocheParcelle.reposeSousLaPioche(main);
                NB_PARCELLE_TOTAL--;
                Assert.assertEquals(NB_PARCELLE_TOTAL, laPiocheParcelle.getPioche().size());
            }
        }
    }
}
