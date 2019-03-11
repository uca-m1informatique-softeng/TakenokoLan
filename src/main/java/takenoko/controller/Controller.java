package takenoko.controller;

import org.springframework.web.bind.annotation.*;
import takenoko.configuration.Takenoko;
import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;

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
        takenoko.takenoko();
        return "done";
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
}
