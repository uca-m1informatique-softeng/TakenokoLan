package joueur.controller;

import joueur.ia.IAPanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {



    @Autowired
    IAPanda iaPanda;



    /*
        private LinkedHashMap<Integer, LinkedHashMap<Integer, IAPanda>> listPlayer = new LinkedHashMap<>();

        @RequestMapping(path = "/newPlayer")
        public String launch() {
            IAPanda iaPanda = new IAPanda();
            int[] tab = iaPanda.connect();
            System.out.println("new player connecté à la partie num : " + tab[0] + " en tant que joueur : " + tab[1]);
            if (listPlayer.get(tab[0]) != null) {
                listPlayer.get(tab[0]).put(tab[1], iaPanda);
            } else {
                listPlayer.put(tab[0], new LinkedHashMap<>());
                listPlayer.get(tab[0]).put(tab[1], iaPanda);
            }
            iaPanda.launch();
            return "done";
        }
    */
    @PostMapping(value = "/{id}/{idPlayer}/Joue")
    public String Joue(@PathVariable(value = "id") int id, @PathVariable(value = "idPlayer") int idPlayer) {
        //listPlayer.get(id).get(idPlayer).joue();
        iaPanda.joue();
        return "done";
    }

}
