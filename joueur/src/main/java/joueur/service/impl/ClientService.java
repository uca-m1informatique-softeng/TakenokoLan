package joueur.service.impl;

import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.FeuilleJoueur;
import commun.ressources.Parcelle;
import commun.triche.TricheException;
import joueur.service.IClientService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class ClientService implements IClientService {

    private String REST_SERVICE_URI = "http://192.168.99.100:8080";

   // private String REST_SERVICE_URI = "http://localhost:8080";

    private RestTemplate restTemplate = new RestTemplate();

   // private String REST_SERVICE_URI_ID_JOUEUR = "http://localhost:8080";
private String REST_SERVICE_URI_ID_JOUEUR = "http://192.168.99.100:8080";
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
    public void feuilleJoueurInitNbAction() {
        restTemplate.postForObject(REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurInitNbAction", null, FeuilleJoueur.class);
    }

    @Override
    public int feuilleJoueurGetNbAction() {
        return restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurGetNbAction",
                HttpMethod.GET,
                null,
                int.class).getBody();
    }

    @Override
    public int feuilleJoueurGetActionChoisie() {
        return restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurGetActionChoisie",
                HttpMethod.GET,
                null,
                int.class).getBody();
    }

    @Override
    public void feuilleJoueurSetActionChoisie(int actionChoisie) {
        restTemplate.postForObject(REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurSetActionChoisie", actionChoisie, int.class);
    }

    @Override
    public int feuilleJoueurGetNbBambouRose() {
        return restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurGetNbBambouRose",
                HttpMethod.GET,
                null,
                int.class).getBody();
    }

    @Override
    public int feuilleJoueurGetNbBambouVert() {
        return restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurGetNbBambouVert",
                HttpMethod.GET,
                null,
                int.class).getBody();
    }

    @Override
    public int feuilleJoueurGetNbBambouJaune() {
        return restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurGetNbBambouJaune",
                HttpMethod.GET,
                null,
                int.class).getBody();
    }

    @Override
    public void feuilleJoueurDecNbACtion() {
        restTemplate.postForObject(REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurDecNbACtion", null, FeuilleJoueur.class);
    }

    @Override
    public void deplacerPanda(Coordonnees coordonnees) throws TricheException {
        HttpEntity<Coordonnees> request = new HttpEntity<>(coordonnees);
        String result = restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/DeplacerPanda",
                HttpMethod.POST,
                request,
                String.class).getBody();
        if (!result.equals("done")) {
            throw new TricheException("impossible de deplacer 2 fois panda");
        }
    }

    @Override
    public void deplacerJardinier(Coordonnees coordonnees) throws TricheException {
        HttpEntity<Coordonnees> request = new HttpEntity<>(coordonnees);
        String result = restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/DeplacerJardinier",
                HttpMethod.POST,
                request,
                String.class).getBody();
        if (!result.equals("done")) {
            throw new TricheException("impossible de deplacer 2 fois jardinier");
        }
    }

    @Override
    public void poserParcelle(Parcelle parcelle) throws TricheException {
        HttpEntity<Parcelle> request = new HttpEntity<>(parcelle);
        String result = restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/PoserParcelle",
                HttpMethod.POST,
                request,
                String.class).getBody();
        if (!result.equals("done")) {
            throw new TricheException("impossible de poser 2 fois une parcelle");
        }
    }

    @Override
    public void piocherUnObjectif(int i) throws TricheException {
        HttpEntity<Integer> request = new HttpEntity<>(i);
        String result = restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/PiocherUnObjectif",
                HttpMethod.POST,
                request,
                String.class).getBody();
        if (!result.equals("done")) {
            throw new TricheException("impossible de piocher 2 fois");
        }
    }

    @Override
    public ArrayList<CartesObjectifs> feuilleJoueurGetMainObjectif() {
        return restTemplate.exchange(
                REST_SERVICE_URI_ID_JOUEUR + "/FeuilleJoueurGetMainObjectif",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<CartesObjectifs>>() {
                }).getBody();
    }

    @Override
    public void verifObjectifAccompli() {
        restTemplate.postForObject(REST_SERVICE_URI_ID_JOUEUR + "/VerifObjectifAccompli", null, CartesObjectifs.class);
    }


    public Coordonnees pandaGetCoordonnees() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/PandaGetCoordonnees",
                HttpMethod.GET,
                null,
                Coordonnees.class).getBody();
    }

    @Override
    public Coordonnees jardinierGetCoordonnees() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/JardinierGetCoordonnees",
                HttpMethod.GET,
                null,
                Coordonnees.class).getBody();
    }

    @Override
    public ArrayList<Parcelle> getZoneJouee() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/GetZoneJouee",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Parcelle>>() {
                }).getBody();
    }

    @Override
    public ArrayList<Coordonnees> getListeZonesPosables() {
        return restTemplate.exchange(
                REST_SERVICE_URI + "/GetListeZonesPosables",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Coordonnees>>() {
                }).getBody();
    }

    @Override
    public int[] connect(String name) {
        int[] tab = restTemplate.exchange(
                        REST_SERVICE_URI + "/" + name + "/Connect",
                        HttpMethod.GET,
                        null,
                        int[].class).getBody();
        REST_SERVICE_URI = REST_SERVICE_URI + "/" + tab[0];
        REST_SERVICE_URI_ID_JOUEUR = REST_SERVICE_URI + "/" + tab[1];
        return tab;
    }

    @Override
    public void launch() {
        restTemplate.postForObject(REST_SERVICE_URI + "/launch", null, int.class);
    }
}
