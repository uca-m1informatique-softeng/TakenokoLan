package serveur.entites;

import commun.ressources.Coordonnees;
import commun.ressources.Parcelle;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class GetNbBambousStepDef {

    private TestRestTemplate template = new TestRestTemplate();
    Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
    Parcelle p2 = new Parcelle(new Coordonnees(0, 1, -1));
    Parcelle p3 = new Parcelle(new Coordonnees(1, 0, -1));
    private ResponseEntity<Integer> response;
    private HttpEntity<Parcelle> requestParcelle;
    private HttpEntity<Coordonnees> requestCoordonnees;
    @Given("^un terrain avec une parcelle de chaque couleur$")
    public void init(){
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    //Le joueur demande le nombre de bambous jaune

    @Given("^une parcelle jaune$")
    public void parcelleJaune(){
        p1.setCouleur(Parcelle.Couleur.JAUNE);

        requestParcelle = new HttpEntity<>(p1);
        template.exchange("http://localhost:8080/0/PoserParcelle", HttpMethod.POST, requestParcelle,
                String.class);
    }

    @And("^un panda qui mange le bambou jaune$")
    public void pandaMangeBambouJaune(){
        requestCoordonnees = new HttpEntity<>(p1.getCoord());
        template.exchange("http://localhost:8080/0/DeplacerPanda", HttpMethod.POST, requestCoordonnees,
                String.class);
    }

    @When("^joueur appelle getBambouJaune$")
    public void joueurAppelleGetBambouJaune(){
        response=template.exchange(
                "http://localhost:8080/0/FeuilleJoueurGetNbBambouJaune",
                HttpMethod.GET,
                null,
                int.class);
    }

    @Then("^reçoit un statu codes (\\d+)$")
    public void statuCode(int statuCode){
        HttpStatus currentStatusCode = response.getStatusCode();
        Assert.assertEquals(statuCode, currentStatusCode.value());
    }

    @Then("^il y a un seul bambou jaune$")
    public void unSeulBambouJaune(){
        Assert.assertEquals(1,(int)response.getBody());
    }

    //Le joueur demande le nombre de bambous vert

    @Given("^une parcelle vert$")
    public void parcelleVert(){
        p2.setCouleur(Parcelle.Couleur.VERTE);
        requestParcelle = new HttpEntity<>(p2);
        template.exchange("http://localhost:8080/0/PoserParcelle", HttpMethod.POST, requestParcelle,
                String.class);
    }

    @And("^un panda qui mange le bambou vert$")
    public void pandaMangeBambouVert(){
        requestCoordonnees = new HttpEntity<>(p2.getCoord());
        template.exchange("http://localhost:8080/0/DeplacerPanda", HttpMethod.POST, requestCoordonnees,
                String.class);
    }

    @When("^joueur appelle getBambouVert$")
    public void joueurAppelleGetBambouVert(){
        response=template.exchange(
                "http://localhost:8080/0/FeuilleJoueurGetNbBambouVert",
                HttpMethod.GET,
                null,
                int.class);
    }

    @Then("^reçoit un status codes (\\d+)$")
    public void statuCode2(int statuCode){
        HttpStatus currentStatusCode = response.getStatusCode();
        Assert.assertEquals(statuCode, currentStatusCode.value());
    }

    @Then("^il y a un seul bambou vert$")
    public void unSeulBambouVert(){
        Assert.assertEquals(1,(int)response.getBody());
    }

    //Le joueur demande le nombre de bambous rose

    @Given("^une parcelle rose$")
    public void parcelleRose(){
        p3.setCouleur(Parcelle.Couleur.ROSE);
        requestParcelle = new HttpEntity<>(p3);
        template.exchange("http://localhost:8080/0/PoserParcelle", HttpMethod.POST, requestParcelle,
                String.class);
    }

    @And("^un panda qui mange le bambou rose$")
    public void pandaMangeBambouRose(){
        requestCoordonnees = new HttpEntity<>(p3.getCoord());
        template.exchange("http://localhost:8080/0/DeplacerPanda", HttpMethod.POST, requestCoordonnees,
                String.class);
    }

    @When("^joueur appelle getBambouRose$")
    public void joueurAppelleGetBambouRose(){
        response=template.exchange(
                "http://localhost:8080/0/FeuilleJoueurGetNbBambouRose",
                HttpMethod.GET,
                null,
                int.class);
    }

    @Then("^reçoit statu code (\\d+)$")
    public void statuCode3(int statuCode){
        HttpStatus currentStatusCode = response.getStatusCode();
        Assert.assertEquals(statuCode, currentStatusCode.value());
    }

    @Then("^il y a un seul bambou rose$")
    public void unSeulBambouRose(){
        Assert.assertEquals(1,(int)response.getBody());
    }
}
