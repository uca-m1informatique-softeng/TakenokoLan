package takenoko.ressources;

import takenoko.ia.IA;
import takenoko.moteur.Terrain;

public class CarteObjectifParcelle extends CartesObjectifs {

    public CarteObjectifParcelle(Motif motif, Parcelle.Couleur couleur, int points) {
        super(couleur, points, motif);
    }

    public CarteObjectifParcelle(Motif motif, Parcelle.Couleur couleur1, Parcelle.Couleur couleur2, int points) {
        super(couleur1, couleur2, points, motif);
    }

    public String toString() {
        if (super.getCouleur2() == null)
            return "Parcelle " + super.getMotif() + " de couleur " + super.getCouleur() + " pour " + super.getPoints() + " points";
        else
            return "Parcelle " + super.getMotif() + " " + super.getCouleur() + "/" + super.getCouleur2() + " pour " + super.getPoints() + " points";
    }

    @Override
    public void verifObjectif(Terrain terrain, Parcelle valeur, IA bot) {
        if (getMotif() != Motif.POINT) {
            switch (getMotif()) {
                case TRIANGLE:
                    detecterTriangle(terrain, valeur, bot);
                    break;
                case LIGNE:
                    detecterLigne(terrain, valeur, bot);
                    break;
                case COURBE:
                    detecterCourbe(terrain, valeur, bot);
                    break;
                case LOSANGE:
                    detecterLosange(terrain, valeur, bot);
                    break;
            }
        }
    }

    private void detecterTriangle(Terrain terrain, Parcelle parcelle, IA bot) {
        if (parcelle.getCouleur() == this.getCouleur()) {
            // G-HG
            if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);

            }
            // HG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HD-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // D-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BD-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);

            }
            // BG-G
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
    }


    private boolean irrigueeEtCouleur(Parcelle p1, Parcelle p2) {
        return (p1.isIrriguee() && p2.isIrriguee() && p1.getCouleur() == this.getCouleur() && p2.getCouleur() == this.getCouleur());
    }

    private void detecterLigne(Terrain terrain, Parcelle parcelle, IA bot) {
        if (parcelle.getCouleur() == this.getCouleur()) {
            // D-D/D
            if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(terrain.getDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // G-D
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // G-G/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(terrain.getGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(terrain.getGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HD-HD/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BG-BG/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getBasGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getBasGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-HG/HG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getHautGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getHautGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BD-BD/BD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
    }

    private void detecterCourbe(Terrain terrain, Parcelle parcelle, IA bot) {
        if (parcelle.getCouleur() == this.getCouleur()) {
            // D-BD/D
            if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // G-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-G/HG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(terrain.getHautGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(terrain.getHautGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BD-D/BD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getBasDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(terrain.getBasDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // G-HG/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BD-BG/BD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getBasDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getBasDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HD-HG/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getHautDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getHautDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BG-BD/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HD-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HG-HD/HG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BG-G/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(terrain.getBasGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(terrain.getBasGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // G-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // D-HD/D
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // HD-D/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // BG-D
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
            // G-BG/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))))) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
    }

    private void detecterLosange(Terrain terrain, Parcelle parcelle, IA bot) {
        // HG-HD-HD/HG
        if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // HD-D-BD
        else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && parcelle.getCouleur() == terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()
                    && parcelle.getCouleur() != terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // HG-G-BG
        else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // BG-BD-BD/BG
        else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getBasDroite(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // HD-D-D/HD
        else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // BG-BD-D
        else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // G-HG-HD
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // G-BG-BG/G
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(terrain.getBasGauche(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // D-BD-BD/D
        else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // G-BG-BD
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // HG-HD-D
        else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
        // G-HG-HG/G
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()) {
                bot.getFeuilleJoueur().majPointsEtMain(this, bot);
            }
        }
    }
}
