package takenoko.ressources;

public class Parcelle {
    private Coordonnees coord;

    public enum Couleur {VERTE, JAUNE, ROSE, SOURCE}

    public enum Effet {PUIT, ENCLOS, ENGRAIS, AUCUN}

    private Couleur couleur;
    private Effet effet;
    private int bambou = 0;
    private boolean irriguee = false;

    public void pousserBambou() {
        if (bambou < 3) {
            bambou++;
        }
    }

    public boolean mangerBambou() {
        if (bambou > 0) {
            bambou--;
            return true;
        } else {
            return false;
        }
    }

    public int getBambou() {
        return bambou;
    }

    public Parcelle(Couleur couleur, Effet effet) {
        this.couleur = couleur;
        this.effet = effet;
        if(effet==Effet.PUIT){
            setIrriguee(true);
        }
    }

    public Parcelle(){
    }

    public Effet getEffet() {
        return effet;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Parcelle(Coordonnees coord) {
        this.coord = coord;
    }

    public Coordonnees getCoord() {
        return coord;
    }

    public void setCoord(Coordonnees coord) {
        this.coord = coord;
    }

    public void setEffet(Effet effet) {
        this.effet = effet;
    }

    public boolean isIrriguee() {
        return irriguee;
    }

    public void setIrriguee(boolean irriguee) {
        this.irriguee = irriguee;
        this.pousserBambou();
    }

    @Override
    public String toString() {
        return "Parcelle{" +
                "coord=" + coord +
                ", couleur=" + couleur +
                ", effet=" + effet +
                ", bambou=" + bambou +
                ", irriguee=" + irriguee +
                '}';
    }
}