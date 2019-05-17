package joueur.lancement;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LancementJoueurs implements ApplicationListener<ApplicationReadyEvent> {
    private String serveurHost="172.18.0.2";
    //private String serveurHost = "localhost";
    private String serveurPort = "8080";

    /**
     * Cet événement est exécuté le plus tard possible pour indiquer
     * que l'application est prête à repondre aux demandes.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        boolean alive;
        RestTemplate restTemplate = new RestTemplate();
        do {
            try {
                restTemplate.exchange("http://" + serveurHost + ":" + serveurPort + "/alive", HttpMethod.GET, null, String.class);
                alive = true;
            } catch (Exception e) {
                System.out.println("Serveur inaccessible");
                alive = false;
                //Pour attendre 10s
                sleep(10000);
            }
        } while (!alive);
        for (int i = 0; i < 2000; i++) {
            sleep(10);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.put("http://localhost:8081" + "/newPlayer", null);
                }
            }).start();
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException a) {
        }
    }
}
