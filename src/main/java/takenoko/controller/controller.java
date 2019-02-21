package takenoko.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takenoko.configuration.Takenoko;
import takenoko.ressources.Parcelle;

import java.util.ArrayList;

@RestController
public class controller {

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
}