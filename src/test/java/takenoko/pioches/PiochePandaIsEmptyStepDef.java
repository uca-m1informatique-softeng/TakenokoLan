package takenoko.pioches;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class PiochePandaIsEmptyStepDef {
    private ResponseEntity<Boolean> response; // output

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Given("^on init une partie$")
    public void onInitUnePartie() {
        testRestTemplate.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /PiochePandaIsEmpty")
    public void clientGETPiochePandaIsEmpty() {
        response = testRestTemplate.getForEntity("http://localhost:8080/PiochePandaIsEmpty", Boolean.class);
    }

    @Then("^le client reçoit un status code (\\d+)$")
    public void clientRecoitUnStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();
        assertEquals(currentStatusCode.value(), statusCode);
    }

    @And("^le client reçoit faux car la pioche est pleine$")
    public void clientRecoitFauxCarLaPiocheEstPleine() {
        assertEquals(response.getBody(), false);
    }


}
