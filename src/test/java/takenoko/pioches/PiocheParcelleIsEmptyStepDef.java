package takenoko.pioches;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import takenoko.SpringRootTest;

import static org.junit.Assert.assertEquals;

public class PiocheParcelleIsEmptyStepDef extends SpringRootTest {
    private ResponseEntity<Boolean> response; // output

    @Given("^une partie$")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /PiocheParcelleIsEmpty$")
    public void clientGETPiocheParcelleIsEmpty() {
        response = template.getForEntity("http://localhost:8080/PiocheParcelleIsEmpty", Boolean.class);
    }

    @Then("^le client reçoit status code (\\d+)$")
    public void clientRecoitStatusCode(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();
        assertEquals(currentStatusCode.value(), statusCode);
    }

    @And("^le client reçoit faux$")
    public void clientRecoitFaux() {
        assertEquals(response.getBody(), false);
    }

    @Given("^la pioche est vide$")
    public void videPioche() {
        //on vide la pioche
        for (int i = 0; i < 9; i++) {
            template.getForEntity("http://localhost:8080/Piocher", String.class);
        }
    }

    @When("^le client appelle /PiocheParcelleIsEmpty alors que la pioche est vide$")
    public void clientGETPiocheParcelleIsEmpty2() {
        response = template.getForEntity("http://localhost:8080/PiocheParcelleIsEmpty", Boolean.class);
    }

    @Then("^le client reçoit vrai$")
    public void clientRecoitVrai() {
        assertEquals(response.getBody(), true);
    }
}