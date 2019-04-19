package serveur.controller;

import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
import commun.ressources.FeuilleJoueur;
import commun.ressources.Parcelle;
import commun.triche.TricheException;
import org.springframework.web.bind.annotation.*;
import serveur.configuration.Takenoko;
import serveur.utilitaires.StatistiqueJoueur;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class Controller {
    private LinkedHashMap<Integer, Takenoko> listParti = new LinkedHashMap<>();

    @GetMapping(value = "/init")
    public String init() {
        listParti.clear();
        listParti.put(0, new Takenoko());
        listParti.get(0).initPartie();
        return "init done";
    }

    @GetMapping(value = "/{namePlayer}/Connect")
    public int[] connect(@PathVariable(value = "namePlayer") String namePlayer) {
        int[] tab = new int[2];
        if (listParti.isEmpty()) {
            int numGame = new Random().nextInt(5000);
            int numPlayer = new Random().nextInt(5000);
            listParti.put(numGame, new Takenoko());
            listParti.get(numGame).getListPlayer().add(new StatistiqueJoueur(numPlayer, 0, 0, 0, namePlayer));
            System.out.println(numGame+" | " +numPlayer+" | "+namePlayer);
            tab[0] = numGame;
            tab[1] = numPlayer;
            return tab;
        } else {
            for (Map.Entry<Integer, Takenoko> entry : listParti.entrySet()) {
                Takenoko game = entry.getValue();
                Integer numGame = entry.getKey();
                if (game.getListPlayer().size() <= 1) {
                    // 1 joueur par parti pour le moment
                    int num = new Random().nextInt(5000) + 1;
                    int numPlayer = new Random().nextInt(5000);
                    listParti.put(num, new Takenoko());
                    listParti.get(num).getListPlayer().add(new StatistiqueJoueur(numPlayer, 0, 0, 0, namePlayer));
                    System.out.println(numGame+" | " +numPlayer+" | "+namePlayer);
                    tab[0] = num;
                    tab[1] = numPlayer;
                    return tab;
                } else {
                    int numPlayer = new Random().nextInt(5000);
                    game.getListPlayer().add(new StatistiqueJoueur(numPlayer, 0, 0, 0, namePlayer));
                    tab[0] = numGame;
                    tab[1] = numPlayer;
                    return tab;
                }
            }
        }
        return tab;
    }

    @PostMapping(path = "/{id}/launch")
    public void launch(@PathVariable(value = "id") int id) {
        if (listParti.get(id).getListPlayer().size() == 1) {
            listParti.get(id).lancerParti(listParti.get(id).getListPlayer());
        }
    }

    @GetMapping(value = "/{id}/GetZoneJouee")
    public ArrayList<Parcelle> getZoneJouee(@PathVariable(value = "id") int id) {
        return listParti.get(id).getTerrain().getZoneJoueeParcelles();
    }

    @GetMapping(value = "/{id}/Piocher")
    public ArrayList<Parcelle> piocher(@PathVariable(value = "id") int id) {
        return listParti.get(id).getLaPiocheParcelle().piocherParcelle();
    }

    @PostMapping(value = "/{id}/ReposeSousLaPioche")
    public void reposeSousLaPioche(@RequestBody ArrayList<Parcelle> aRemettre, @PathVariable(value = "id") int id) {
        listParti.get(id).getLaPiocheParcelle().reposeSousLaPioche(aRemettre);
    }

    @GetMapping(value = "/{id}/PiocheParcelleIsEmpty")
    public Boolean piocheParcelleIsEmpty(@PathVariable(value = "id") int id) {
        return listParti.get(id).getLaPiocheParcelle().getPioche().isEmpty();
    }

    @GetMapping(value = "/{id}/PandaGetDeplacementsPossible")
    public ArrayList<Coordonnees> pandaGetDeplacementsPossible(@PathVariable(value = "id") int id) {
        return listParti.get(id).getPanda().getDeplacementsPossible(listParti.get(id).getTerrain().getZoneJouee());
    }

    @GetMapping(value = "/{id}/PiochePandaIsEmpty")
    public Boolean piochePandaIsEmpty(@PathVariable(value = "id") int id) {
        return listParti.get(id).getLesPiochesObjectif().getLaPiocheObjectifsPanda().getPioche().isEmpty();
    }


    @GetMapping(value = "/{id}/JardinierGetDeplacementsPossible")
    public ArrayList<Coordonnees> jardinierGetDeplacementsPossible(@PathVariable(value = "id") int id) {
        return listParti.get(id).getJardinier().getDeplacementsPossible(listParti.get(id).getTerrain().getZoneJouee());
    }

    @PostMapping(value = "/{id}/FeuilleJoueurInitNbAction")
    public void feuilleJoueurInitNbAction(@PathVariable(value = "id") int id) {
        listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().initNbAction();
    }

    @GetMapping(value = "/{id}/FeuilleJoueurGetNbAction")
    public int feuilleJoueurGetNbAction(@PathVariable(value = "id") int id) {
        return listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().getNbAction();
    }

    @GetMapping(value = "/{id}/FeuilleJoueurGetActionChoisie")
    public int feuilleJoueurGetActionChoisie(@PathVariable(value = "id") int id) {
        return listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().getActionChoisie();
    }

    @PostMapping(value = "/{id}/FeuilleJoueurSetActionChoisie")
    public void FeuilleJoueurSetActionChoisie(@RequestBody int actioneChoisie, @PathVariable(value = "id") int id) {
        listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().setActionChoisie(actioneChoisie);
    }

    @GetMapping(value = "/{id}/FeuilleJoueurGetNbBambouRose")
    public int feuilleJoueurGetNbBambouRose(@PathVariable(value = "id") int id) {
        return listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().getNbBambouRose();
    }

    @GetMapping(value = "/{id}/FeuilleJoueurGetNbBambouVert")
    public int feuilleJoueurGetNbBambouVert(@PathVariable(value = "id") int id) {
        return listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().getNbBambouVert();
    }

    @GetMapping(value = "/{id}/FeuilleJoueurGetNbBambouJaune")
    public int feuilleJoueurGetNbBambouJaune(@PathVariable(value = "id") int id) {
        return listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().getNbBambouJaune();
    }

    @PostMapping(value = "/{id}/FeuilleJoueurDecNbACtion")
    public void FeuilleJoueurSetActionChoisie(@PathVariable(value = "id") int id) {
        listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().decNbACtion();
    }

    @PostMapping(value = "/{id}/DeplacerPanda")
    public String deplacerPanda(@RequestBody Coordonnees coordonnees, @PathVariable(value = "id") int id) {
        try {
            listParti.get(id).getPanda().deplacerEntite(coordonnees, listParti.get(id).getListPlayer().get(0).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/{id}/DeplacerJardinier")
    public String deplacerJardinier(@RequestBody Coordonnees coordonnees, @PathVariable(value = "id") int id) {
        try {
            listParti.get(id).getJardinier().deplacerEntite(coordonnees, listParti.get(id).getListPlayer().get(0).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/{id}/PoserParcelle")
    public String poserParcelle(@RequestBody Parcelle parcelle, @PathVariable(value = "id") int id) {
        try {
            listParti.get(id).getTerrain().changements(parcelle, listParti.get(id).getListPlayer().get(0).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/{id}/PiocherUnObjectif")
    public String piocherUnObjectif(@RequestBody int i, @PathVariable(value = "id") int id) {
        try {
            listParti.get(id).getLesPiochesObjectif().piocherUnObjectif(listParti.get(id).getListPlayer().get(0).getFeuilleJoueur(), i);
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @GetMapping(value = "/{id}/FeuilleJoueurGetMainObjectif")
    public ArrayList<CartesObjectifs> feuilleJoueurGetMainObjectif(@PathVariable(value = "id") int id) {
        return listParti.get(id).getListPlayer().get(0).getFeuilleJoueur().getMainObjectif();
    }

    @PostMapping(value = "/{id}/VerifObjectifAccompli")
    public void verifObjectifAccompli(@PathVariable(value = "id") int id) {
        listParti.get(id).getTerrain().verifObjectifAccompli(listParti.get(id).getListPlayer().get(0).getFeuilleJoueur());
    }


    @GetMapping(value = "/{id}/PandaGetCoordonnees")
    public Coordonnees pandaGetCoordonnees(@PathVariable(value = "id") int id) {
        return listParti.get(id).getPanda().getCoordonnees();
    }

    @GetMapping(value = "/{id}/JardinierGetCoordonnees")
    public Coordonnees jardinierGetCoordonnees(@PathVariable(value = "id") int id) {
        return listParti.get(id).getJardinier().getCoordonnees();
    }

    @GetMapping(value = "/{id}/GetListeZonesPosables")
    public ArrayList<Coordonnees> getListeZonesPosables(@PathVariable(value = "id") int id) {
        return listParti.get(id).getTerrain().getListeZonesPosables();
    }
}

