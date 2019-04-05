package joueur.ia;

import commun.moteur.Terrain;
import commun.pioches.LaPiocheParcelle;
import commun.ressources.Coordonnees;
import commun.ressources.FeuilleJoueur;
import commun.ressources.Parcelle;
import commun.triche.TricheException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import joueur.service.impl.ClientService;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class PoserParcelleStepDef {

    LaPiocheParcelle laPiocheParcelle;
    Terrain terrain;
    IAPanda IAPanda = new IAPanda();


    @Given("^un terrain")
    public void initTerrain() {
        terrain = new Terrain();
    }


    @And("^une pioche de parcelles")
    public void initPiocheParcelles() {
        laPiocheParcelle = new LaPiocheParcelle();
    }

    @When("^la parcelle est posée")
    public void poseParcelle() throws TricheException {
        initTerrain();
        initPiocheParcelles();
        laPiocheParcelle.getPioche().clear();
        FeuilleJoueur feuilleJoueur = new FeuilleJoueur("bob");
        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        laPiocheParcelle.getPioche().add(p1);

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.feuilleJoueurGetNbAction()).thenReturn(feuilleJoueur.getNbAction(), 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(feuilleJoueur.getActionChoisie(), 0, 0, 1, 1);
        when(iService.getZoneJouee()).thenReturn(terrain.getZoneJoueeParcelles());
        when(iService.getListeZonesPosables()).thenReturn(terrain.getListeZonesPosables());
        try {
            doAnswer(
                    new Answer() {
                        @Override
                        public Object answer(InvocationOnMock invocation) throws Throwable {
                            terrain.changements(p1, feuilleJoueur);
                            return null;
                        }
                    }).when(iService).poserParcelle(p1);
        } catch (TricheException e) {

        }

        IAPanda.joue();

    }

    @Then("^le nombre de parcelles sur le terrain augmente du nombre de parcelles posees")
    public void nbParcellesZoneJouee() {
        Assert.assertEquals(2, terrain.getZoneJouee().size());
    }

}
