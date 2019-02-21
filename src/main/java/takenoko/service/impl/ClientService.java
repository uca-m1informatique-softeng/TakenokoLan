package takenoko.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import takenoko.ressources.Parcelle;
import takenoko.service.IClientService;

import java.util.ArrayList;

@Service
public class ClientService implements IClientService {

    private RestTemplate restTemplate= new RestTemplate();

    @Override
    public ArrayList<Parcelle> piocher() {
        return restTemplate.exchange(
                "http://localhost:8080/Piocher",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Parcelle>>() {
                }).getBody();
    }
}
