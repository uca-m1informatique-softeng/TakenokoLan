package joueur.lancement;

import joueur.ia.IAPanda;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Component
public class LancementJoueurs implements ApplicationListener<ApplicationReadyEvent> {
    private LinkedHashMap<Integer, LinkedHashMap<Integer, IAPanda>> listPlayer = new LinkedHashMap<>();
    //private String serveurHost="172.18.0.2";
    private String serveurHost = "localhost";
    private String serveurPort = "8080";

    /**
     * Cet événement est exécuté le plus tard possible pour indiquer
     * que l'application est prête à repondre aux demandes.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        boolean alive;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8081/setList",String.class);
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
                    //restTemplate.put("http://localhost:8081" + "/newPlayer", null);
                    int idP=restTemplate.exchange(
                           "http://localhost:8081" + "/newPlayer",
                            HttpMethod.GET,
                            null,
                            int.class).getBody();
                    boolean start = false;
                    synchronized (listPlayer) {
                        if (listPlayer.get(idP).size() == 2) {
                            start = true;
                        }
                    }
                    if (start) {
                        listPlayer.get(idP).get(0).launch();
                    }
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

    public void setListPlayer(LinkedHashMap<Integer, LinkedHashMap<Integer, IAPanda>> listPlayer) {
        this.listPlayer = listPlayer;
    }
}
