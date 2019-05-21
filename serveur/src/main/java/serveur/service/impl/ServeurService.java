package serveur.service.impl;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import serveur.service.IServeurService;

@Service
public class ServeurService implements IServeurService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void JoueClient(String urlJoueur,int id, int idPlayer) {
        restTemplate.exchange(urlJoueur + "/" + id + "/" + idPlayer + "/Joue", HttpMethod.POST, null, String.class);
    }

    @Override
    public void finPartie(String urlJoueur) {
        restTemplate.exchange(urlJoueur +"/nbfin", HttpMethod.POST, null, String.class);
    }
}