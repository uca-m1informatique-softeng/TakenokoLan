package joueur.newjoueur;

import org.springframework.web.client.RestTemplate;

public class NewJoueur implements Runnable {

    @Override
    public void run() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8081/newPlayer", null);
    }
}
