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
        do {
            try {
                restTemplate.exchange("http://" + "172.18.0.2" + ":" + "8080" + "/alive", HttpMethod.GET, null, String.class);
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
    }
}
