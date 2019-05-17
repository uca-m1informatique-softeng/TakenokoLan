package joueur.lancement;

import joueur.newjoueur.NewJoueur;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LancementJoueurs implements ApplicationListener<ApplicationReadyEvent> {
    private String serveurHost="172.18.0.2";
    //private String serveurHost="localhost";
    private String serveurPort="8080";
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
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException a) {
                }
            }
        } while (!alive);
        for (int i = 0; i < 600; i++) {
            new Thread(new NewJoueur()).start();
        }
    }
}
