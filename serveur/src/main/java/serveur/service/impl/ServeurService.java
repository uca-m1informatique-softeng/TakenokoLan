package serveur.service.impl;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import serveur.service.IServeurService;

@Service
public class ServeurService implements IServeurService {

    //private String REST_SERVICE_URI = "http://192.168.99.100:8081";
    private String REST_SERVICE_URI = "http://localhost:8081";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void JoueClient(int id, int idPlayer) {
        restTemplate.exchange(REST_SERVICE_URI + "/" + id + "/" + idPlayer + "/Joue", HttpMethod.POST, null, String.class);
    }
}
