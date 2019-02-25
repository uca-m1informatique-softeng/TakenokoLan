package takenoko.ia;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.service.impl.ClientService;
import static org.mockito.Mockito.when;

public class PoserParcelleStepDef {

    LaPiocheParcelle laPiocheParcelle;
    Terrain terrain;
    Jardinier jardinier;
    Panda panda;
    LesPiochesObjectif lesPiochesObjectif;
    IAPanda IAPanda;

    @Given("^un terrain")
    public void initTerrain(){
        terrain = new Terrain();
    }


    @And("^une pioche de parcelles")
    public void initPiocheParcelles() {
         laPiocheParcelle = new LaPiocheParcelle();
    }

    @When("^la parcelle est posée")
    public void poseParcelle()  {
        initTerrain();
        initPiocheParcelles();
        IAPanda = new IAPanda();

        jardinier = new Jardinier(terrain);
        panda = new Panda(terrain);

        lesPiochesObjectif = new LesPiochesObjectif();

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());

        IAPanda.joue(laPiocheParcelle, terrain, lesPiochesObjectif, jardinier, panda);

    }

    @Then("^le nombre de parcelles sur le terrain augmente du nombre de parcelles posees")
    public void nbParcellesZoneJouee()  {
        Assert.assertEquals(2, terrain.getZoneJouee().size());
    }

}
