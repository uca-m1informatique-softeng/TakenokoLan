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
                restTemplate.exchange("http://" + "172.0.0.1" + ":" + "8081" + "/alive", HttpMethod.GET, null, String.class);
                alive2 = true;
                System.out.println("joueur accessible 172.0.0.1");
            } catch (Exception e) {
                System.out.println("joueur inaccessible 172.0.0.1");
                alive2 = false;
            }
            try {
                restTemplate.exchange("http://" + "172.18.0.2" + ":" + "8081" + "/alive", HttpMethod.GET, null, String.class);
                alive2 = true;
                System.out.println("joueur accessible 172.18.0.2");
            } catch (Exception e) {
                System.out.println("joueur inaccessible 172.18.0.2");
                alive2 = false;
            }
            try {
                restTemplate.exchange("http://" + "172.18.0.2" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
                alive = true;
                InetAddress address = InetAddress.getLocalHost();
                String ip = address.getHostAddress();
                System.out.println("Serveur accessible 172.18.0.2 : ");
            } catch (Exception e) {
                System.out.println("Serveur inaccessible 172.18.0.2");
                alive = false;
            }
            try {
                restTemplate.exchange("http://" + "172.18.0.3" + ":" + "8081" + "/alive", HttpMethod.GET, null, String.class);
                alive2 = true;
                System.out.println("joueur accessible 172.18.0.3");
            } catch (Exception e) {
                System.out.println("joueur inaccessible 172.18.0.3");
                alive2 = false;
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
