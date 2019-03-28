package ressources;


import moteur.Terrain;

import java.util.ArrayList;

public class CarteObjectifJardinier extends CartesObjectifs {

    public CarteObjectifJardinier() {
        super();
    }

    public CarteObjectifJardinier(int nbBambou, Parcelle.Couleur couleur, int points, Motif motif, Parcelle.Effet effet) {
        super(couleur, points, motif, nbBambou, effet);
    }

    public CarteObjectifJardinier(int nbBambou, Parcelle.Couleur couleur, int points, Motif motif) {
        super(couleur, points, motif, nbBambou);
    }

    public String toString() {
        return "Jardinier " + super.getMotif() + " de couleur " + super.getCouleur() + " pour " + super.getPoints() + " points pour " + super.getBambou() + " bambou";
    }

    @Override
    public void verifObjectif(Terrain terrain, Parcelle valeur, FeuilleJoueur feuilleJoueur) {
        if (getMotif() != Motif.COURBE) {
            switch (getMotif()) {
                case POINT:
                    detecterBambou(valeur, feuilleJoueur);
                    break;
                case LIGNE:
                    detecterLigneBambou(terrain, valeur, feuilleJoueur);
                    break;
                case TRIANGLE:
                    detecterTriangleBambou(terrain, valeur, feuilleJoueur);
                    break;
                case LOSANGE:
                    detecterLosangeBambou(terrain, valeur, feuilleJoueur);
                    break;
            }
        }
    }

    private void detecterBambou(Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou() && parcelle.getEffet() == this.getEffet()) {
            feuilleJoueur.majPointsEtMain(this);
        }
    }

    private void detecterLigneBambou(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        int i = 0;
        ArrayList<Coordonnees> adja = terrain.getAdjacents(parcelle.getCoord());
        ArrayList<Parcelle> par = new ArrayList<>();
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou()) {
            for (Coordonnees p : adja) {
                par.add(terrain.getZoneJouee().get(p));
            }
            for (Parcelle p : par) {
                if (p != null && p.getCouleur() == this.getCouleur() && this.getBambou() == p.getBambou()) {
                    i++;
                }
            }
            if (i != 0) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
    }

    private boolean bambouEtCouleur(Parcelle p1, Parcelle p2) {
        return (p1.getBambou() == this.getBambou() && p2.getBambou() == this.getBambou() && p2.isIrriguee() && p1.getCouleur() == this.getCouleur() && p2.getCouleur() == this.getCouleur());
    }

    private void detecterTriangleBambou(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou()) {
            // G-HG
            if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HD-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // D-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BD-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BG-G
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
    }

    private void detecterLosangeBambou(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou()) {
            // HG-HD-HD/HG
            if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // HD-D-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord()))) {
                if (terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // HG-G-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord()))) {
                if (terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // BG-BD-BD/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // HD-D-D/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // BG-BD-D
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord()))) {
                if (terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // G-HG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord()))) {
                if (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // G-BG-BG/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // D-BD-BD/D
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // G-BG-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord()))) {
                if (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // HG-HD-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord()))) {
                if (terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
            // G-HG-HG/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    feuilleJoueur.majPointsEtMain(this);
                }
            }
        }
    }
}
