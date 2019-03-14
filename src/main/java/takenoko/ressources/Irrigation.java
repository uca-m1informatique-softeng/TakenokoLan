package takenoko.ressources;

public class Irrigation {

    private Coordonnees coord1;
    private Coordonnees coord2;

    public Irrigation(Coordonnees coord1, Coordonnees coord2) {
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public Coordonnees getCoord1() {
        return coord1;
    }

    public Coordonnees getCoord2() {
        return coord2;
    }

    public String toString() {
        return getCoord1() + " / " + getCoord2();
    }
}
