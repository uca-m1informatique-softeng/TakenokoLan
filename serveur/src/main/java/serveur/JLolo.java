package serveur;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@Component
public class JLolo implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            System.out.println("local :" + InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
        }
        System.out.println("loopback" + InetAddress.getLoopbackAddress().getHostAddress());
        boolean alive;
        boolean alive1;
        RestTemplate restTemplate = new RestTemplate();

        boolean alive2;
        do {
            try {
                restTemplate.exchange("http://" + "nat.gce-us-central1.travisci.net" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
                alive2 = true;
                System.out.println("serveur accessible nat.gce-us-central1.travisci.net");
            } catch (Exception e) {
                System.out.println("serveur inaccessible nat.gce-us-central1.travisci.net");
                alive2 = false;
            }
            try {
                restTemplate.exchange("http://" + "127.0.0.1" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
                alive2 = true;
                System.out.println("serveur accessible 127.0.0.1");
            } catch (Exception e) {
                System.out.println("serveur inaccessible 127.0.0.1");
                alive2 = false;
            }
            try {
                restTemplate.exchange("http://" + "localhost" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
                alive = true;
                InetAddress address = InetAddress.getLocalHost();
                String ip = address.getHostAddress();
                System.out.println("Serveur accessible localhost:8080 : "+ip);
            } catch (Exception e) {
                System.out.println("Serveur inaccessible localhost:8080");
                alive = false;
            }
            try {
                restTemplate.exchange("http://" + "localhost" + ":" + "8081" + "/alive", HttpMethod.GET, null, String.class);
                alive1 = true;
                System.out.println("Joueur accessible localhost:8081");
            } catch (Exception e) {
                System.out.println("Joueur inaccessiblelocalhost:8081");
                alive1 = false;
            }
            //Pour attendre 10s
            try {
                Thread.sleep(10000);
            } catch (InterruptedException a) {
            }
        } while (!alive2 && !alive && !alive1);
    }
}
