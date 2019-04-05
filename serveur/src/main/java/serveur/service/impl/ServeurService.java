package serveur.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import serveur.service.IServeurService;

@Service
public class ServeurService implements IServeurService {
    private static String REST_SERVICE_URI = "http://localhost:8081";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void JoueClient(int idPlauer) {
        HttpEntity<Integer> request = new HttpEntity<>(idPlauer);
        restTemplate.exchange(REST_SERVICE_URI + "/Joue", HttpMethod.POST, request, String.class);
    }
}
