package joueur.controller;

import joueur.ia.IAPanda;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class Controller {
    private LinkedHashMap<Integer, IAPanda> listPlayer = new LinkedHashMap<>();

    @RequestMapping(path = "/newPlayer")
    public String launch() {
        System.out.println("new player");
        IAPanda iaPanda = new IAPanda();
        int num = iaPanda.connect();
        System.out.println("connect "+num);
        listPlayer.put(num, iaPanda);
        System.out.println("debut launch "+num);
        iaPanda.launch();
        System.out.println("fin lauch "+num);
        return "done";
    }

    @PostMapping(value = "/{idPlayer}/Joue")
    public String Joue(@PathVariable(value = "idPlayer") int idPlayer) {
        listPlayer.get(idPlayer).joue();
        return "done";
    }
}
