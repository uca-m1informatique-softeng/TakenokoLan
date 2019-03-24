package takenoko.ressources;

import java.util.ArrayList;
import java.util.HashMap;

public class Coordonnees {
    private int x;
    private int y;
    private int z;

    public Coordonnees() {
    }

    public Coordonnees(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Coordonnees)) {
            return false;
        }
        return (x == ((Coordonnees) o).x && y == ((Coordonnees) o).y && z == ((Coordonnees) o).z);
    }

    @Override
    public String toString() {
        return "Coordonnees{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public int hashCode() {
        // nombres random
        int result = 17;
        result = 31 * result + getX();
        result = 31 * result + getY();
        result = 31 * result + getZ();
        return result;
    }

    public void deplacementPossible(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible) {
        Coordonnees coordonnees = new Coordonnees(x, y, z);
        partieGauche(zoneJoue, deplacementsPossible, coordonnees);
        partieDroite(zoneJoue, deplacementsPossible, coordonnees);
        partieHautGauche(zoneJoue, deplacementsPossible, coordonnees);
        partieBasDroite(zoneJoue, deplacementsPossible, coordonnees);
        partieHautDroite(zoneJoue, deplacementsPossible, coordonnees);
        partieBasGauche(zoneJoue, deplacementsPossible, coordonnees);

    }

//-------------------------------------------------------------------------------------------------------------------------
//coordonneTMP est la coordonnee qui varie a chaque fois

    //en horizontal gauche
    private void partieGauche(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees gauche = new Coordonnees(coordonneTMP.getX() - 1, coordonneTMP.getY() + 1, coordonneTMP.getZ());
        if (zoneJoue.containsKey(gauche)) {
            partieGauche(zoneJoue, deplacementsPossible, gauche);
            deplacementsPossible.add(gauche);
        }

    }

    // en horizontal droite
    private void partieDroite(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees droite = new Coordonnees(coordonneTMP.getX() + 1, coordonneTMP.getY() - 1, coordonneTMP.getZ());
        if (zoneJoue.containsKey(droite)) {
            partieDroite(zoneJoue, deplacementsPossible, droite);
            deplacementsPossible.add(droite);
        }
    }

    //en diagonale haut gauche
    private void partieHautGauche(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees hautGauche = new Coordonnees(coordonneTMP.getX(), coordonneTMP.getY() + 1, coordonneTMP.getZ() - 1);
        if (zoneJoue.containsKey(hautGauche)) {
            partieHautGauche(zoneJoue, deplacementsPossible, hautGauche);
            deplacementsPossible.add(hautGauche);
        }
    }

    //en diagonale bas droite
    private void partieBasDroite(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees basDroite = new Coordonnees(coordonneTMP.getX(), coordonneTMP.getY() - 1, coordonneTMP.getZ() + 1);
        if (zoneJoue.containsKey(basDroite)) {
            partieBasDroite(zoneJoue, deplacementsPossible, basDroite);
            deplacementsPossible.add(basDroite);
        }
    }

    //en diagonale haut droite
    private void partieHautDroite(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees hautDroite = new Coordonnees(coordonneTMP.getX() + 1, coordonneTMP.getY(), coordonneTMP.getZ() - 1);
        if (zoneJoue.containsKey(hautDroite)) {
            partieHautDroite(zoneJoue, deplacementsPossible, hautDroite);
            deplacementsPossible.add(hautDroite);
        }
    }

    //en diagonale bas gauche
    private void partieBasGauche(HashMap<Coordonnees, Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees basGauche = new Coordonnees(coordonneTMP.getX() - 1, coordonneTMP.getY(), coordonneTMP.getZ() + 1);
        if (zoneJoue.containsKey(basGauche)) {
            partieBasGauche(zoneJoue, deplacementsPossible, basGauche);
            deplacementsPossible.add(basGauche);
        }
    }
}