package joueur;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class Lancement1000PartiesIT {
    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        boolean fin = false;
        int nb = 0;
        do {
            nb = restTemplate.exchange("http://localhost:8081/getnbFin", HttpMethod.GET, null, int.class).getBody();
            if (nb == 1000) {
                fin = true;
            } else {
                fin = false;
                System.out.println("En cours");
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                }
            }
        } while (!fin);

        nb = restTemplate.exchange("http://localhost:8081/getnbFin", HttpMethod.GET, null, int.class).getBody();
        Assert.assertEquals(1000, nb);
    }
}
