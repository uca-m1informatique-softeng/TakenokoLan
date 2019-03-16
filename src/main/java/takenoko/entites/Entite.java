package takenoko.entites;

import org.springframework.beans.factory.annotation.Autowired;
import takenoko.moteur.Terrain;
import takenoko.ressources.Coordonnees;
import takenoko.ressources.FeuilleJoueur;
import takenoko.utilitaires.TricheException;

public abstract class Entite {
    @Autowired
    protected Terrain terrain;
    @Autowired
    protected Coordonnees coordonnees;

    public abstract Coordonnees getCoordonnees();

    public abstract void deplacerEntite(Coordonnees coordonnees, FeuilleJoueur feuilleJoueur) throws TricheException;


}
