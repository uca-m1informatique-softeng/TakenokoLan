package joueur.controller;

import commun.ressources.Parcelle;
import joueur.ia.IAPanda;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Controller {
    private ArrayList<IAPanda> listPlayer = new ArrayList<>();

    @RequestMapping(path = "/newPlayer")
    public String launch() {
        IAPanda iaPanda = new IAPanda();
        listPlayer.add(iaPanda);
        iaPanda.connect();
        return "done";
    }

    @PostMapping(value = "/Joue")
    public String Joue(@RequestBody int idPlayer) {
        listPlayer.get(0).joue();
        return "done";
    }
}
