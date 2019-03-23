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

    public void deplacementPossible(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible) {
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
    private void partieGauche(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees gauche = new Coordonnees(coordonneTMP.getX() - 1, coordonneTMP.getY() + 1, coordonneTMP.getZ());
        for (int i = 0; i < zoneJoue.size(); i++) {
            if (zoneJoue.get(i).getCoord().equals(gauche)) {
                partieGauche(zoneJoue, deplacementsPossible, gauche);
                deplacementsPossible.add(gauche);
            }
        }
    }

    // en horizontal droite
    private void partieDroite(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees droite = new Coordonnees(coordonneTMP.getX() + 1, coordonneTMP.getY() - 1, coordonneTMP.getZ());
        for (int i = 0; i < zoneJoue.size(); i++) {
            if (zoneJoue.get(i).getCoord().equals(droite)) {
                partieDroite(zoneJoue, deplacementsPossible, droite);
                deplacementsPossible.add(droite);
            }
        }
    }

    //en diagonale haut gauche
    private void partieHautGauche(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees hautGauche = new Coordonnees(coordonneTMP.getX(), coordonneTMP.getY() + 1, coordonneTMP.getZ() - 1);
        for (int i = 0; i < zoneJoue.size(); i++) {
            if (zoneJoue.get(i).getCoord().equals(hautGauche)) {
                partieHautGauche(zoneJoue, deplacementsPossible, hautGauche);
                deplacementsPossible.add(hautGauche);
            }
        }
    }

    //en diagonale bas droite
    private void partieBasDroite(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees basDroite = new Coordonnees(coordonneTMP.getX(), coordonneTMP.getY() - 1, coordonneTMP.getZ() + 1);
        for (int i = 0; i < zoneJoue.size(); i++) {
            if (zoneJoue.get(i).getCoord().equals(basDroite)) {
                partieBasDroite(zoneJoue, deplacementsPossible, basDroite);
                deplacementsPossible.add(basDroite);
            }
        }
    }

    //en diagonale haut droite
    private void partieHautDroite(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees hautDroite = new Coordonnees(coordonneTMP.getX() + 1, coordonneTMP.getY(), coordonneTMP.getZ() - 1);
        for (int i = 0; i < zoneJoue.size(); i++) {
            if (zoneJoue.get(i).getCoord().equals(hautDroite)) {
                partieHautDroite(zoneJoue, deplacementsPossible, hautDroite);
                deplacementsPossible.add(hautDroite);
            }
        }
    }

    //en diagonale bas gauche
    private void partieBasGauche(ArrayList<Parcelle> zoneJoue, ArrayList<Coordonnees> deplacementsPossible, Coordonnees coordonneTMP) {
        Coordonnees basGauche = new Coordonnees(coordonneTMP.getX() - 1, coordonneTMP.getY(), coordonneTMP.getZ() + 1);
        for (int i = 0; i < zoneJoue.size(); i++) {
            if (zoneJoue.get(i).getCoord().equals(basGauche)) {
                partieBasGauche(zoneJoue, deplacementsPossible, basGauche);
                deplacementsPossible.add(basGauche);
            }
        }
    }
}
