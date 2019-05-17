package joueur.controller;

import joueur.ia.IAPanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class Controller {

    @Autowired
    IAPanda iaPanda;

    @PostMapping(value = "/{id}/{idPlayer}/Joue")
    public String Joue(@PathVariable(value = "id") int id, @PathVariable(value = "idPlayer") int idPlayer) {
        //listPlayer.get(id).get(idPlayer).joue();
        iaPanda.joue();
        return "done";
    }


}
