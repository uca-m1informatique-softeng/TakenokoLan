package takenoko.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import takenoko.ressources.Parcelle;
import takenoko.service.IClientService;
import takenoko.utilitaires.Coordonnees;

import java.util.ArrayList;

@Service
public class ClientService implements IClientService {
    private static String REST_SERVICE_URI = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public ArrayList<Parcelle> piocher() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/Piocher",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Parcelle>>() {
                }).getBody();
    }

    /**
     * Méthode pour reposer les parcelles sous la pioche
     *
     * @param aRemettre les parcelles piocher que le bot doit remettre sous la pioche
     */
    @Override
    public void reposeSousLaPioche(ArrayList<Parcelle> aRemettre) {
        restTemplate.postForLocation(REST_SERVICE_URI + "/ReposeSousLaPioche", aRemettre, new ParameterizedTypeReference<ArrayList<Parcelle>>() {
        });
    }

    @Override
    public Boolean piocheParcelleIsEmpty() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/PiocheParcelleIsEmpty",
                HttpMethod.GET,
                null,
                boolean.class).getBody();
    }

    @Override
    public ArrayList<Coordonnees> pandaGetDeplacementsPossible() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/PandaGetDeplacementsPossible",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Coordonnees>>() {
                }).getBody();
    }

    @Override
    public Boolean piochePandaIsEmpty() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/PiochePandaIsEmpty",
                HttpMethod.GET,
                null,
                boolean.class).getBody();
    }

    @Override
    public ArrayList<Coordonnees> jardinierGetDeplacementsPossible() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/JardinierGetDeplacementsPossible",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Coordonnees>>() {
                }).getBody();
    }

    @Override
    public Coordonnees pandaGetCoordonnees() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/PandaGetCoordonnees",
                HttpMethod.GET,
                null,
                Coordonnees.class).getBody();
    }
}
