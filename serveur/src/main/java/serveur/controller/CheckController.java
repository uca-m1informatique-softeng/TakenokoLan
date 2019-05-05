package serveur.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CheckController {


    @RequestMapping(path = "/alive")
    public ResponseEntity<String> postData(HttpServletResponse response) {
        response.setHeader("Allow", "OPTIONS");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}