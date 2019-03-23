package takenoko.controller;

import org.springframework.web.bind.annotation.*;
import takenoko.configuration.Takenoko;
import takenoko.ia.IA;
import takenoko.ressources.CartesObjectifs;
import takenoko.ressources.Coordonnees;
import takenoko.ressources.FeuilleJoueur;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.StatistiqueJoueur;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;

@RestController
public class Controller {

    private Takenoko takenoko;

    @GetMapping(value = "/init")
    public String init() {
        takenoko = new Takenoko();
        takenoko.initPartie();
        return "init done";
    }

    @RequestMapping(path = "/launch")
    public String launch() {
        takenoko = new Takenoko();
        ArrayList<StatistiqueJoueur> listPlayer = new ArrayList<>();
        listPlayer.add(new StatistiqueJoueur(IA.newIA(IA.Type.PANDA), 0, 0, 0, "IAPANDA"));
        takenoko.lancerParti(listPlayer);
        return "done";
    }

    @GetMapping(value = "/GetZoneJouee")
    public ArrayList<Parcelle> getZoneJouee() {
        return takenoko.getTerrain().getZoneJoueeParcelles();
    }

    @PostMapping(value = "/CoordToParcelle")
    public Parcelle coordToParcelle(@RequestBody Coordonnees coordonnees) {
        return takenoko.getTerrain().coordToParcelle(coordonnees);
    }

    @GetMapping(value = "/Piocher")
    public ArrayList<Parcelle> piocher() {
        return takenoko.getLaPiocheParcelle().piocherParcelle();
    }

    @PostMapping(value = "/ReposeSousLaPioche")
    public void reposeSousLaPioche(@RequestBody ArrayList<Parcelle> aRemettre) {
        takenoko.getLaPiocheParcelle().reposeSousLaPioche(aRemettre);
    }

    @GetMapping(value = "/PiocheParcelleIsEmpty")
    public Boolean piocheParcelleIsEmpty() {
        return takenoko.getLaPiocheParcelle().getPioche().isEmpty();
    }

    @GetMapping(value = "/PandaGetDeplacementsPossible")
    public ArrayList<Coordonnees> pandaGetDeplacementsPossible() {
        return takenoko.getPanda().getDeplacementsPossible(takenoko.getTerrain().getZoneJouee());
    }

    @GetMapping(value = "/PiochePandaIsEmpty")
    public Boolean piochePandaIsEmpty() {
        return takenoko.getLesPiochesObjectif().getLaPiocheObjectifsPanda().getPioche().isEmpty();
    }


    @GetMapping(value = "/JardinierGetDeplacementsPossible")
    public ArrayList<Coordonnees> jardinierGetDeplacementsPossible() {
        return takenoko.getJardinier().getDeplacementsPossible(takenoko.getTerrain().getZoneJouee());
    }


    @GetMapping(value = "/GetFeuilleJoueur")
    public FeuilleJoueur getFeuilleJoueur() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur();
    }

    @PostMapping(value = "/FeuilleJoueurInitNbAction")
    public void feuilleJoueurInitNbAction() {
        takenoko.getListPlayer().get(0).getFeuilleJoueur().initNbAction();
    }

    @GetMapping(value = "/FeuilleJoueurGetNbAction")
    public int feuilleJoueurGetNbAction() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbAction();
    }

    @GetMapping(value = "/FeuilleJoueurGetActionChoisie")
    public int feuilleJoueurGetActionChoisie() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur().getActionChoisie();
    }

    @PostMapping(value = "/FeuilleJoueurSetActionChoisie")
    public void FeuilleJoueurSetActionChoisie(@RequestBody int actioneChoisie) {
        takenoko.getListPlayer().get(0).getFeuilleJoueur().setActionChoisie(actioneChoisie);
    }

    @GetMapping(value = "/FeuilleJoueurGetNbBambouRose")
    public int feuilleJoueurGetNbBambouRose() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbBambouRose();
    }

    @GetMapping(value = "/FeuilleJoueurGetNbBambouVert")
    public int feuilleJoueurGetNbBambouVert() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbBambouVert();
    }

    @GetMapping(value = "/FeuilleJoueurGetNbBambouJaune")
    public int feuilleJoueurGetNbBambouJaune() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbBambouJaune();
    }

    @PostMapping(value = "/FeuilleJoueurDecNbACtion")
    public void FeuilleJoueurSetActionChoisie() {
        takenoko.getListPlayer().get(0).getFeuilleJoueur().decNbACtion();
    }

    @PostMapping(value = "/DeplacerPanda")
    public String deplacerPanda(@RequestBody Coordonnees coordonnees) {
        try {
            takenoko.getPanda().deplacerEntite(coordonnees, takenoko.getListPlayer().get(0).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/DeplacerJardinier")
    public String deplacerJardinier(@RequestBody Coordonnees coordonnees) {
        try {
            takenoko.getJardinier().deplacerEntite(coordonnees, takenoko.getListPlayer().get(0).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/PoserParcelle")
    public String poserParcelle(@RequestBody Parcelle parcelle) {
        try {
            takenoko.getTerrain().changements(parcelle, takenoko.getListPlayer().get(0).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/PiocherUnObjectif")
    public String piocherUnObjectif(@RequestBody int i) {
        try {
            takenoko.getLesPiochesObjectif().piocherUnObjectif(takenoko.getListPlayer().get(0).getFeuilleJoueur(), i);
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @GetMapping(value = "/FeuilleJoueurGetMainObjectif")
    public ArrayList<CartesObjectifs> feuilleJoueurGetMainObjectif() {
        return takenoko.getListPlayer().get(0).getFeuilleJoueur().getMainObjectif();
    }

    @PostMapping(value = "/VerifObjectifAccompli")
    public void verifObjectifAccompli() {
        takenoko.getTerrain().verifObjectifAccompli(takenoko.getListPlayer().get(0).getFeuilleJoueur());
    }


    @GetMapping(value = "/PandaGetCoordonnees")
    public Coordonnees pandaGetCoordonnees() {
        return takenoko.getPanda().getCoordonnees();
    }

    @GetMapping(value = "/JardinierGetCoordonnees")
    public Coordonnees jardinierGetCoordonnees() {
        return takenoko.getJardinier().getCoordonnees();
    }
}

