package takenoko.controller;

import org.springframework.web.bind.annotation.*;
import takenoko.configuration.Takenoko;
import takenoko.ressources.Parcelle;

import java.util.ArrayList;

@RestController
public class Controller {

    private Takenoko takenoko;

    @RequestMapping(path = "/launch")
    public String launch() {
        takenoko = new Takenoko();
        takenoko.Takenoko();
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
    public Boolean reposeSousLaPioche() {
        return takenoko.getLaPiocheParcelle().getPioche().isEmpty();
    }
}
