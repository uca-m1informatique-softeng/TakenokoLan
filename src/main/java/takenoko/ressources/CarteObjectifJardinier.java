package takenoko.ressources;

import takenoko.ia.IA;
import takenoko.moteur.Terrain;
import takenoko.utilitaires.Coordonnees;

import java.util.ArrayList;

public class CarteObjectifJardinier extends CartesObjectifs {

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
    public void verifObjectif(Terrain terrain, Parcelle valeur, IA bot) {
        if (getMotif() != Motif.COURBE) {
            switch (getMotif()) {
                case POINT:
                    detecterBambou(valeur, bot);
                    break;
                case LIGNE:
                    detecterLigneBambou(terrain, valeur, bot);
                    break;
                case TRIANGLE:
                    detecterTriangleBambou(terrain, valeur, bot);
                    break;
                case LOSANGE:
                    detecterLosangeBambou(terrain, valeur, bot);
                    break;
            }
        }
    }

    private void detecterBambou(Parcelle parcelle, IA bot) {
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou() && parcelle.getEffet() == this.getEffet()) {
            bot.getFeuilleJoueur().majPointsEtMain(this, bot);
        }
    }

    private void detecterLigneBambou(Terrain terrain, Parcelle parcelle, IA bot) {
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
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
    }

    private boolean bambouEtCouleur(Parcelle p1, Parcelle p2) {
        return (p1.getBambou() == this.getBambou() && p2.getBambou() == this.getBambou() && p2.isIrriguee() && p1.getCouleur() == this.getCouleur() && p2.getCouleur() == this.getCouleur());
    }

    private void detecterTriangleBambou(Terrain terrain, Parcelle parcelle, IA bot) {
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou()) {
            // G-HG
            if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HD-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // D-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BD-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BG-G
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    bambouEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
    }

    private void detecterLosangeBambou(Terrain terrain, Parcelle parcelle, IA bot) {
        if (parcelle.getCouleur() == this.getCouleur() && parcelle.getBambou() == this.getBambou()) {
            // HG-HD-HD/HG
            if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord())))) {
                if (terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getBambou() == this.getBambou() &&
                        terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() &&
                        terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
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
                    bot.getFeuilleJoueur().majPointsEtMain(this, bot);
                }
            }
        }
    }
}
