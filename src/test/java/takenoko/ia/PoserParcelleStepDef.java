package takenoko.ia;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.internal.exceptions.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import takenoko.configuration.Takenoko;
import takenoko.entites.Jardinier;
import takenoko.entites.Panda;
import takenoko.moteur.Terrain;
import takenoko.pioches.LaPiocheParcelle;
import takenoko.pioches.LesPiochesObjectif;
import takenoko.ressources.Parcelle;
import takenoko.service.impl.ClientService;
import takenoko.utilitaires.StatistiqueJoueur;
import takenoko.utilitaires.TricheException;

import java.util.ArrayList;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class PoserParcelleStepDef {

    LaPiocheParcelle laPiocheParcelle;
    Terrain terrain;
    IAPanda IAPanda;


    Takenoko takenoko=new Takenoko();

    @Given("^un terrain")
    public void initTerrain(){
        terrain = takenoko.getTerrain();
    }


    @And("^une pioche de parcelles")
    public void initPiocheParcelles() {
         laPiocheParcelle = takenoko.getLaPiocheParcelle();
    }

    @When("^la parcelle est posée")
    public void poseParcelle()  {
        takenoko.initPartie();
        initTerrain();
        initPiocheParcelles();
        ArrayList<StatistiqueJoueur> listPlayer = new ArrayList<StatistiqueJoueur>();
        listPlayer.add(new StatistiqueJoueur(IA.newIA(IA.Type.PANDA), 0, 0, 0,""));
        takenoko.setListPlayer(listPlayer);

        ClientService iService = Mockito.mock(ClientService.class);
        takenoko.getListPlayer().get(0).getIa().setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.feuilleJoueurGetNbAction()).thenReturn(takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbAction(),1,0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(takenoko.getListPlayer().get(0).getFeuilleJoueur().getActionChoisie(),0,0,1,1);

        takenoko.getListPlayer().get(0).getIa().joue(laPiocheParcelle, terrain, takenoko.getLesPiochesObjectif(), takenoko.getJardinier(), takenoko.getPanda());

    }

    @Then("^le nombre de parcelles sur le terrain augmente du nombre de parcelles posees")
    public void nbParcellesZoneJouee()  {
        Assert.assertEquals(2, terrain.getZoneJouee().size());
    }

}
