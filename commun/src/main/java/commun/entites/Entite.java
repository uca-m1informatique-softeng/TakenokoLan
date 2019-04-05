package commun.entites;

import org.springframework.beans.factory.annotation.Autowired;
import commun.moteur.Terrain;
import commun.ressources.Coordonnees;
import commun.ressources.FeuilleJoueur;
import commun.triche.TricheException;

public abstract class Entite {
    @Autowired
    protected Terrain terrain;
    @Autowired
    protected Coordonnees coordonnees;

    public abstract Coordonnees getCoordonnees();

    public abstract void deplacerEntite(Coordonnees coordonnees, FeuilleJoueur feuilleJoueur) throws TricheException;


}
