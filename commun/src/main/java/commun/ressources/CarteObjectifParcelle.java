package commun.ressources;

import commun.moteur.Terrain;

public class CarteObjectifParcelle extends CartesObjectifs {

    public CarteObjectifParcelle() {
        super();
    }

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
    public void verifObjectif(Terrain terrain, Parcelle valeur, FeuilleJoueur feuilleJoueur) {
        if (getMotif() != Motif.POINT) {
            switch (getMotif()) {
                case TRIANGLE:
                    detecterTriangle(terrain, valeur, feuilleJoueur);
                    break;
                case LIGNE:
                    detecterLigne(terrain, valeur, feuilleJoueur);
                    break;
                case COURBE:
                    detecterCourbe(terrain, valeur, feuilleJoueur);
                    break;
                case LOSANGE:
                    detecterLosange(terrain, valeur, feuilleJoueur);
                    break;
            }
        }
    }

    private void detecterTriangle(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        if (parcelle.getCouleur() == this.getCouleur()) {
            // G-HG
            if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);

            }
            // HG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HD-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // D-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BD-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);

            }
            // BG-G
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
    }


    private boolean irrigueeEtCouleur(Parcelle p1, Parcelle p2) {
        return (p1.isIrriguee() && p2.isIrriguee() && p1.getCouleur() == this.getCouleur() && p2.getCouleur() == this.getCouleur());
    }

    private void detecterLigne(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        if (parcelle.getCouleur() == this.getCouleur()) {
            // D-D/D
            if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(terrain.getDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // G-D
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // G-G/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(terrain.getGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(terrain.getGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HD-HD/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BG-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BG-BG/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getBasGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getBasGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-HG/HG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getHautGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getHautGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BD-BD/BD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
    }

    private void detecterCourbe(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        if (parcelle.getCouleur() == this.getCouleur()) {
            // D-BD/D
            if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // G-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-G/HG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(terrain.getHautGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(terrain.getHautGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BD-D/BD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getBasDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(terrain.getBasDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-D
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // G-HG/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BD-BG/BD
            else if (terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getBasDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getBasDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-BG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HD-HG/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getHautDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getHautDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BG-BD/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HD-BD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HG-HD/HG
            else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BG-G/BG
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getGauche(terrain.getBasGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getGauche(terrain.getBasGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // G-HD
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // D-HD/D
            else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // HD-D/HD
            else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // BG-D
            else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())))) {
                feuilleJoueur.majPointsEtMain(this);
            }
            // G-BG/G
            else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) &&
                    terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))) &&
                    irrigueeEtCouleur(terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())), terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))))) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
    }

    private void detecterLosange(Terrain terrain, Parcelle parcelle, FeuilleJoueur feuilleJoueur) {
        // HG-HD-HD/HG
        if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // HD-D-BD
        else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && parcelle.getCouleur() == terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()
                    && parcelle.getCouleur() != terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // HG-G-BG
        else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // BG-BD-BD/BG
        else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getBasGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getBasDroite(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // HD-D-D/HD
        else if (terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(terrain.getHautDroite(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // BG-BD-D
        else if (terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // G-HG-HD
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // G-BG-BG/G
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(terrain.getGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(terrain.getBasGauche(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // D-BD-BD/D
        else if (terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasDroite(terrain.getDroite(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // G-BG-BD
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getBasDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getBasGauche(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getBasDroite(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // HG-HD-D
        else if (terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautDroite(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getDroite(parcelle.getCoord()))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getDroite(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() != parcelle.getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getHautDroite(parcelle.getCoord())).getCouleur() == terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
        // G-HG-HG/G
        else if (terrain.getZoneJouee().containsKey(terrain.getGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(parcelle.getCoord())) && terrain.getZoneJouee().containsKey(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord())))) {
            if (parcelle.getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() && terrain.getZoneJouee().get(terrain.getHautGauche(terrain.getGauche(parcelle.getCoord()))).getCouleur() == this.getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            } else if ((parcelle.getCouleur() == this.getCouleur() || parcelle.getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == parcelle.getCouleur()
                    && terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() != terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()
                    && (terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur() || terrain.getZoneJouee().get(terrain.getGauche(parcelle.getCoord())).getCouleur() == this.getCouleur2())
                    && terrain.getZoneJouee().get(terrain.getGauche(terrain.getHautGauche(parcelle.getCoord()))).getCouleur() == terrain.getZoneJouee().get(terrain.getHautGauche(parcelle.getCoord())).getCouleur()) {
                feuilleJoueur.majPointsEtMain(this);
            }
        }
    }
}
