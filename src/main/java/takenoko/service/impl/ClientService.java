package takenoko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import takenoko.configuration.RestTemplateConfig;
import takenoko.ressources.Parcelle;
import takenoko.service.IClientService;

import java.util.ArrayList;

@Service
public class ClientService implements IClientService {

    @Autowired
    private RestTemplateConfig restTemplate;

    @Override
    public ArrayList<Parcelle> piocher() {
        return restTemplate.restTemplate().exchange(
                "http://localhost:8080/Piocher",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Parcelle>>() {
                }).getBody();
    }
}
