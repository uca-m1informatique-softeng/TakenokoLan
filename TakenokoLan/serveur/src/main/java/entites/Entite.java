package entites;

import org.springframework.beans.factory.annotation.Autowired;
import moteur.Terrain;
import ressources.Coordonnees;
import ressources.FeuilleJoueur;
import triche.TricheException;

public abstract class Entite {
    @Autowired
    protected Terrain terrain;
    @Autowired
    protected Coordonnees coordonnees;

    public abstract Coordonnees getCoordonnees();

    public abstract void deplacerEntite(Coordonnees coordonnees, FeuilleJoueur feuilleJoueur) throws TricheException;


}
