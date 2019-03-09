package takenoko.pioches;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import takenoko.SpringRootTest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PiocheParcelleIsEmpty extends SpringRootTest {
    private ResponseEntity<Boolean> response; // output

    @Given("^une partie")
    public void init() {
        template.getForEntity("http://localhost:8080/init", String.class);
    }

    @When("^le client appelle /PiocheParcelleIsEmpty$")
    public void client_GET_PiocheParcelleVide$() {
        response = template.getForEntity("http://localhost:8080/PiocheParcelleIsEmpty", Boolean.class);
    }

    @Then("^le client reçoit status code (\\d+)$")
    public void client_recoit_status_code(int statusCode) {
        HttpStatus currentStatusCode = response.getStatusCode();

        assertThat("status code is incorrect : " + response.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^le client reçoit faux")
    public void client_recoit_faux() {
        assertEquals(response.getBody(), false);
    }
}
