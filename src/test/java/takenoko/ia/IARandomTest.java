package takenoko.ia;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.Coordonnees;

import java.util.Random;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IARandomTest {
    IARandom IARandom = new IARandom();
    Terrain terrain = new Terrain();
    LesPiochesObjectif lesPiochesObjectif = new LesPiochesObjectif();
    Jardinier jardinier = new Jardinier(terrain);
    Panda panda = new Panda(terrain);
    LaPiocheParcelle laPiocheParcelle = new LaPiocheParcelle();

    @Test
    public void BotBobTest() {
        Random random = Mockito.mock(Random.class);
        IARandom.setRand(random);
        when(random.nextInt(2)).thenReturn(0);
        when(random.nextInt(5)).thenReturn(1);
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(2, terrain.getZoneJouee().size());
        Assert.assertEquals(1, IARandom.getMainObjectif().size());

        //deplace panda jardinier
        when(random.nextInt(5)).thenReturn(3, 2);
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertNotEquals(new Coordonnees(0, 0, 0), panda.getCoordonnees());
        Assert.assertNotEquals(new Coordonnees(0, 0, 0), jardinier.getCoordonnees());

        for (int i = 2; i <= 5; i++) {
            when(random.nextInt(5)).thenReturn(1, 0);
            when(random.nextBoolean()).thenReturn(false);
            IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
            Assert.assertEquals(i, IARandom.getMainObjectif().size());
        }

        when(random.nextInt(5)).thenReturn(1, 2, 3);
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(5, IARandom.getMainObjectif().size());

        //pioche une irrigation, deplace le panda a la premiere parcelle de la liste
        when(random.nextInt(5)).thenReturn(4, 2, 0);
        when(random.nextBoolean()).thenReturn(false);
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(1, IARandom.getFeuilleJoueur().getNb_irrigation());

        //placer irrigation
        when(random.nextInt(5)).thenReturn(0, 1, 3);
        when(random.nextBoolean()).thenReturn(true);//poser irrigation
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(0, IARandom.getFeuilleJoueur().getNb_irrigation());

        //pioche une irrigation, deplace le panda a la premiere parcelle de la liste
        when(random.nextInt(5)).thenReturn(4, 2, 0);
        when(random.nextBoolean()).thenReturn(false);
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(1, IARandom.getFeuilleJoueur().getNb_irrigation());
        //pioche une irrigation, deplace le panda a la premiere parcelle de la liste
        when(random.nextInt(5)).thenReturn(4, 2, 0);
        when(random.nextBoolean()).thenReturn(false);
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(2, IARandom.getFeuilleJoueur().getNb_irrigation());

        //placer irrigation
        when(random.nextInt(5)).thenReturn(0, 1, 3);
        when(random.nextBoolean()).thenReturn(true);//poser irrigation
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(1, IARandom.getFeuilleJoueur().getNb_irrigation());

        int i = terrain.getZoneJouee().size();
        while (laPiocheParcelle.getPioche().size() != 0) {
            i++;
            when(random.nextInt(5)).thenReturn(0, 4);
            when(random.nextBoolean()).thenReturn(true);//poser irrigation
            IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
            Assert.assertEquals(i, terrain.getZoneJouee().size());
        }
        //on essaye de piocher une parcelle quand la pioche est vide
        when(random.nextInt(5)).thenReturn(0, 2, 3);
        when(random.nextBoolean()).thenReturn(false);//poser irrigation
        IARandom.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);
        Assert.assertEquals(i, terrain.getZoneJouee().size());
    }
}
