package serveur.ia;

import commun.moteur.Terrain;
import commun.ressources.Coordonnees;
import commun.ressources.Parcelle;
import commun.triche.TricheException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import joueur.ia.IAPanda;
import joueur.service.impl.ClientService;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import serveur.configuration.Takenoko;
import serveur.pioches.LaPiocheParcelle;
import serveur.utilitaires.StatistiqueJoueur;

import java.util.ArrayList;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class PoserParcelleStepDef {

    LaPiocheParcelle laPiocheParcelle;
    Terrain terrain;
    IAPanda IAPanda= new IAPanda();


    Takenoko takenoko = new Takenoko();

    @Given("^un terrain")
    public void initTerrain() {
        terrain = takenoko.getTerrain();
    }


    @And("^une pioche de parcelles")
    public void initPiocheParcelles() {
        laPiocheParcelle = takenoko.getLaPiocheParcelle();
    }

    @When("^la parcelle est posée")
    public void poseParcelle()throws TricheException {
        takenoko.initPartie();
        initTerrain();
        initPiocheParcelles();
        ArrayList<StatistiqueJoueur> listPlayer = new ArrayList<StatistiqueJoueur>();
        listPlayer.add(new StatistiqueJoueur(1, 0, 0, 0, ""));
        takenoko.setListPlayer(listPlayer);
        laPiocheParcelle.getPioche().clear();

        Parcelle p1 = new Parcelle(new Coordonnees(-1, 1, 0));
        p1.setCouleur(Parcelle.Couleur.ROSE);
        laPiocheParcelle.getPioche().add(p1);

        ClientService iService = Mockito.mock(ClientService.class);
        IAPanda.setiService(iService);
        when(iService.piocher()).thenReturn(laPiocheParcelle.piocherParcelle());
        when(iService.feuilleJoueurGetNbAction()).thenReturn(takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbAction(), 1, 0);
        when(iService.feuilleJoueurGetActionChoisie()).thenReturn(takenoko.getListPlayer().get(0).getFeuilleJoueur().getActionChoisie(), 0, 0, 1, 1);
        when(iService.getZoneJouee()).thenReturn(takenoko.getTerrain().getZoneJoueeParcelles());
        when(iService.getListeZonesPosables()).thenReturn(takenoko.getTerrain().getListeZonesPosables());
        try {
            doAnswer(
                    new Answer() {
                        @Override
                        public Object answer(InvocationOnMock invocation) throws Throwable {
                            takenoko.getTerrain().changements(p1, takenoko.getListPlayer().get(0).getFeuilleJoueur());
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
