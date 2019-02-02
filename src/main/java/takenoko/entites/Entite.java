package takenoko.entites;

import org.springframework.beans.factory.annotation.Autowired;
import takenoko.ia.IA;
import takenoko.moteur.Terrain;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Entite {
    @Autowired
    protected Terrain terrain;
    @Autowired
    protected Coordonnees coordonnees;

    public abstract Coordonnees getCoordonnees();

    public abstract void deplacerEntite(Coordonnees coordonnees, IA bot)throws TricheException;


}
