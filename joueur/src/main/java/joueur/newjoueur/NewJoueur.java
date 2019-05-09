package joueur.newjoueur;

import org.springframework.web.client.RestTemplate;

public class NewJoueur implements Runnable {
    // private String REST_SERVICE_URI = "http://192.168.99.100:8081";
    private String REST_SERVICE_URI = "http://localhost:8081";
    @Override
    public void run() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(REST_SERVICE_URI + "/newPlayer", null);
    }
}
