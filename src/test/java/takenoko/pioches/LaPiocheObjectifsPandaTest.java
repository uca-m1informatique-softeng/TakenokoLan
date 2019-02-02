package takenoko.pioches;

import org.junit.Assert;
import org.junit.Test;
import takenoko.ressources.Parcelle;

public class LaPiocheObjectifsPandaTest {

    @Test
    public void lapPiocheObjectifsPandaTest() {
        LaPiocheObjectifsPanda laPiocheObjectifsPanda = new LaPiocheObjectifsPanda();
        if(laPiocheObjectifsPanda.getPioche().size()==30){
            //si la pioche est doubler
            verif(laPiocheObjectifsPanda,2);
        }else{
            //si la pioche n'est pas doubler
            verif(laPiocheObjectifsPanda,1);
        }
    }

    private void verif(LaPiocheObjectifsPanda laPiocheObjectifsPanda,int multiplicateur){


        Assert.assertEquals(15*multiplicateur, laPiocheObjectifsPanda.getPioche().size());

        int j = 0;
        int r = 0;
        int v = 0;
        int multi = 0; //3 couleurs
        for (int i = 0; i < laPiocheObjectifsPanda.getPioche().size(); i++) {
            if (laPiocheObjectifsPanda.getPioche().get(i).getCouleur2() != null) {
                multi++;
            }else {
                if (laPiocheObjectifsPanda.getPioche().get(i).getCouleur() == Parcelle.Couleur.JAUNE) j++;
                if (laPiocheObjectifsPanda.getPioche().get(i).getCouleur() == Parcelle.Couleur.ROSE) r++;
                if (laPiocheObjectifsPanda.getPioche().get(i).getCouleur() == Parcelle.Couleur.VERTE) v++;
            }
        }
        Assert.assertEquals(3*multiplicateur, multi);
        Assert.assertEquals(4*multiplicateur, j);
        Assert.assertEquals(3*multiplicateur, r);
        Assert.assertEquals(5*multiplicateur, v);

        laPiocheObjectifsPanda.piocher();
        Assert.assertEquals((15*multiplicateur-1), laPiocheObjectifsPanda.getPioche().size());
    }
}
