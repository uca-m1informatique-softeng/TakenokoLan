package serveur;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
    }
}
