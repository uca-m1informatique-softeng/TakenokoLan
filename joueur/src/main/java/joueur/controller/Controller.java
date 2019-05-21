package joueur.controller;

import joueur.ia.IAPanda;
import joueur.lancement.LancementJoueurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

@RestController
public class Controller {
    @Autowired
    LancementJoueurs lancementJoueurs;

    int fin=0;

    private LinkedHashMap<Integer, LinkedHashMap<Integer, IAPanda>> listPlayer = new LinkedHashMap<>();

    @RequestMapping(path = "/newPlayer")
    public int launch() {
        IAPanda iaPanda = new IAPanda();
        int[] tab = null;
        try {
            tab = iaPanda.connect("172.18.0.2", "8080", InetAddress.getLocalHost().getHostAddress(), "8081");

            System.out.println("new player connecté à la partie num : " + tab[0] + " en tant que joueur : " + tab[1]);
            synchronized (listPlayer) {
                if (listPlayer.get(tab[0]) != null) {
                    listPlayer.get(tab[0]).put(tab[1], iaPanda);
                } else {
                    listPlayer.put(tab[0], new LinkedHashMap<>());
                    listPlayer.get(tab[0]).put(tab[1], iaPanda);
                }

            }
        } catch (UnknownHostException e) {
        }
        return tab[0];
    }

    @PostMapping(value = "/{id}/{idPlayer}/Joue")
    public String Joue(@PathVariable(value = "id") int id, @PathVariable(value = "idPlayer") int idPlayer) {
        listPlayer.get(id).get(idPlayer).joue();
        //System.out.println("joue -> partie : "+id+" joueur id : "+idPlayer);
        return "done";
    }

    @RequestMapping(path = "/{id}/nbfin")
    public String nbFin(@PathVariable(value = "id") int id){
        System.out.println("fin partie id : "+id);
       fin++;
       return "done";
    }

    @RequestMapping(path = "/setList")
    public String setList(){
        lancementJoueurs.setListPlayer(listPlayer);
        return "done";
    }
    @GetMapping(path = "/getnbFin")
    public int getnbFin(){
        return fin;
    }

}
