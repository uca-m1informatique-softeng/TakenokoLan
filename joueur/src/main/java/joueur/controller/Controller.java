package joueur.controller;

import joueur.ia.IAPanda;
import joueur.lancement.LancementJoueurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

@RestController
public class Controller {
    @Autowired
    LancementJoueurs lancementJoueurs;
    private LinkedHashMap<Integer, LinkedHashMap<Integer, IAPanda>> listPlayer = new LinkedHashMap<>();

    @RequestMapping(path = "/newPlayer")
    public String launch() {
        IAPanda iaPanda = new IAPanda();
        try {
            int[] tab = iaPanda.connect("172.18.0.2", "8080", InetAddress.getLocalHost().getHostAddress(), "8081");

        System.out.println("new player connecté à la partie num : " + tab[0] + " en tant que joueur : " + tab[1]);
        if (listPlayer.get(tab[0]) != null) {
            listPlayer.get(tab[0]).put(tab[1], iaPanda);
        } else {
            listPlayer.put(tab[0], new LinkedHashMap<>());
            listPlayer.get(tab[0]).put(tab[1], iaPanda);
        }
        }catch (UnknownHostException e){}
        iaPanda.launch();
        return "done";
    }

    @PostMapping(value = "/{id}/{idPlayer}/Joue")
    public String Joue(@PathVariable(value = "id") int id, @PathVariable(value = "idPlayer") int idPlayer) {
        listPlayer.get(id).get(idPlayer).joue();
        return "done";
    }
}
