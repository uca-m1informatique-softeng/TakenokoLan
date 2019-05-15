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

        RestTemplate restTemplate = new RestTemplate();

        boolean alive2;
        do {
            try {
                restTemplate.exchange("http://" + "172.18.0.10" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
                alive2 = true;
                System.out.println("serveur accessible");
            } catch (Exception e) {
                System.out.println("serveur inaccessible");
                alive2 = false;
                //Pour attendre 10s
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException a) {
                }
            }
        } while (!alive2);
        do {
            try {
                restTemplate.exchange("http://" + "localhost" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
                alive = true;
                InetAddress address = InetAddress.getLocalHost();
                String ip = address.getHostAddress();
                System.out.println("Serveur accessible : "+ip);
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
        boolean alive1;
        do {
            try {
                restTemplate.exchange("http://" + "localhost" + ":" + "8081" + "/alive", HttpMethod.GET, null, String.class);
                alive1 = true;
                System.out.println("Joueur accessible");
            } catch (Exception e) {
                System.out.println("Joueur inaccessible");
                alive1 = false;
                //Pour attendre 10s
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException a) {
                }
            }
        } while (!alive1);

    }
}
