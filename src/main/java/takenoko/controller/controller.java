package takenoko.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takenoko.configuration.Takenoko;

@RestController
public class controller {

    private Takenoko takenoko;

    @RequestMapping(path = "/launch")
    public String launch() {
        takenoko = new Takenoko();
        takenoko.Takenoko();
        return "done";
    }
}
