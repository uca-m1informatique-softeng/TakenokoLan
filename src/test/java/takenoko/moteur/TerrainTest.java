package takenoko.moteur;

import org.junit.Assert;
import org.junit.Test;
import takenoko.ia.IA;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TerrainTest {

    private Terrain terrain = new Terrain();

    @Test
    public void terrainTest()throws TricheException {

        Parcelle source = new Parcelle(new Coordonnees(0, 0, 0));
        source.setCouleur(Parcelle.Couleur.SOURCE);
        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
        Parcelle p3 = new Parcelle(new Coordonnees(1, 0, -1));
        Parcelle p4 = new Parcelle(new Coordonnees(1, -1, 0));
        Parcelle p5 = new Parcelle(new Coordonnees(0, -1, 1));
        Parcelle p6 = new Parcelle(new Coordonnees(-1, 0, 1));

        // CHANGEMENT TERRAIN
        HashMap<Coordonnees, Integer> zoneDispo = new HashMap<>();
        zoneDispo.put(p1.getCoord(), 2);
        zoneDispo.put(p2.getCoord(), 3);
        zoneDispo.put(p3.getCoord(), 2);
        zoneDispo.put(p4.getCoord(), 2);
        zoneDispo.put(p5.getCoord(), 2);
        zoneDispo.put(p6.getCoord(), 3);

        ArrayList<Coordonnees> listeZonesPosablesAvtCoup = new ArrayList<>();
        listeZonesPosablesAvtCoup.add(p2.getCoord());
        listeZonesPosablesAvtCoup.add(p3.getCoord());
        listeZonesPosablesAvtCoup.add(p4.getCoord());
        listeZonesPosablesAvtCoup.add(p5.getCoord());
        listeZonesPosablesAvtCoup.add(p6.getCoord());

        LinkedHashMap<Coordonnees, Parcelle> zoneJouee = new LinkedHashMap<>();
        terrain.getZoneJouee().put(source.getCoord(), source);

        terrain.changements(p1,IA.newIA(IA.Type.RANDOM));
        ArrayList<Coordonnees> listeZonesPosablesAprCoup = terrain.getListeZonesPosables();
        Assert.assertTrue("Problème changement terrain", contenir(listeZonesPosablesAvtCoup, listeZonesPosablesAprCoup));

        // IRRIGATION
        Parcelle p7 = new Parcelle(new Coordonnees(-1, 2, -1));

        // parcelle irriguée car proximité de la source
        terrain.changements(p2,IA.newIA(IA.Type.RANDOM));
        Assert.assertTrue(p2.isIrriguee());

        // parcelle non irriguée car non connectée via une irrigation
        terrain.changements(p7,IA.newIA(IA.Type.RANDOM));
        Assert.assertFalse(p7.isIrriguee());

        // parcelle irriguée car connectée à une irrigation
        Assert.assertEquals(3, terrain.getListeIrrigationsDispo().size());
        Assert.assertEquals(1, terrain.getListeIrrigationsPosables().size());
        Assert.assertEquals(0, terrain.getListeIrrigationsJouees().size());
        terrain.majIrrigations("bob", 0);
        Assert.assertEquals(2, terrain.getListeIrrigationsDispo().size());
        Assert.assertEquals(2, terrain.getListeIrrigationsPosables().size());
        Assert.assertEquals(1, terrain.getListeIrrigationsJouees().size());

        // test estUnTriangle
        Parcelle p8 = new Parcelle(new Coordonnees(-2, 3, -1));
        Assert.assertFalse(terrain.estUnTriangle(p7.getCoord(), p8.getCoord(), p7.getCoord(), p1.getCoord()));
        Assert.assertTrue(terrain.estUnTriangle(p7.getCoord(), p1.getCoord(), p2.getCoord(), p1.getCoord()));
    }

    private boolean contenir(ArrayList<Coordonnees> listeZonesPosables1, ArrayList<Coordonnees> listeZonesPosables2) {
        for (int i = 0; i < listeZonesPosables1.size(); i++) {
            for (int j = 0; j < listeZonesPosables2.size(); j++) {
                if (listeZonesPosables1.get(i).getX() == listeZonesPosables2.get(j).getX() && listeZonesPosables1.get(i).getY() == listeZonesPosables2.get(j).getY() && listeZonesPosables1.get(i).getZ() == listeZonesPosables2.get(j).getZ()) {
                    return true;
                }
            }
        }
        return false;
    }
}