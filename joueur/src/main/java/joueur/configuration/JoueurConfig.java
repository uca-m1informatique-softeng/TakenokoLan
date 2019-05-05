package joueur.configuration;

import joueur.newjoueur.NewJoueur;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JoueurConfig {

    @Bean
    JoueurConfig lancement() {
        boolean alive;
        RestTemplate restTemplate = new RestTemplate();
        do {
            try {
                restTemplate.exchange("http://localhost:8080/alive", HttpMethod.GET, null, String.class);
                alive = true;
            } catch (Exception e) {
                alive = false;
            }
        } while (!alive);
        for (int i = 0; i < 30; i++) {
            new Thread(new NewJoueur()).start();
        }
        return new JoueurConfig();
    }
}
