package joueur.service.impl;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class ClientServiceIT {

    @Test
    public void connection() {
        ClientService iService = new ClientService();
        String name = "Bob";
        String serveurHost = "172.18.0.2";
        String serveurPort = "8080";
        String joueurHost = null;
        try {
            joueurHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        String joueurPort = "8081";
        int[] tab = new int[2];

        try {
            tab = iService.connect(name,serveurHost,serveurPort,joueurHost,joueurPort);
        } catch (Exception e) {
            fail("Connection test failed: Unable to connect to the server.");
        }

        assertTrue("Error, game numero beyond 5000.",0 <= tab[0] & tab[0] < 5000);
        assertEquals(0,tab[1]);

    }
}

