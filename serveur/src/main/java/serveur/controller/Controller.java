package serveur.controller;

import commun.ressources.CartesObjectifs;
import commun.ressources.Coordonnees;
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
    int nbjoueur = 1;

    @GetMapping(value = "/init")
    public String init() {
        listParti.clear();
        listParti.put(0, new Takenoko());
        listParti.get(0).initPartie();
        listParti.get(0).getListPlayer().add(new StatistiqueJoueur(0, 0, 0, 0, "joueurTest", "http://localhost:8080"));
        return "init done";
    }

    @GetMapping(value = "/{joueurHost}/{joueurPort}/{namePlayer}/Connect")
    public int[] connect(@PathVariable(value = "joueurHost") String joueurHost, @PathVariable(value = "joueurPort") String joueurPort, @PathVariable(value = "namePlayer") String namePlayer) {
        synchronized (listParti) {
            int[] tab = new int[2];
            if (listParti.isEmpty()) {
                int numGame;
                do {
                    numGame = new Random().nextInt(5000);

                } while (listParti.get(numGame) != null);
                int numPlayer = 0;
                listParti.put(numGame, new Takenoko());
                listParti.get(numGame).getListPlayer().add(new StatistiqueJoueur(numPlayer, 0, 0, 0, namePlayer, "http://" + joueurHost + ":" + joueurPort));
                System.out.println("new Game");
                System.out.println("ID partie | id joueur | nom joueur");
                System.out.println(numGame + " | " + numPlayer + " | " + namePlayer);
                tab[0] = numGame;
                tab[1] = numPlayer;
                return tab;
            } else {
                boolean noGame = false;
                for (Map.Entry<Integer, Takenoko> entry : listParti.entrySet()) {
                    Takenoko game = entry.getValue();
                    Integer numGame = entry.getKey();
                    if (game.getListPlayer().size() < nbjoueur) {
                        int numPlayer = game.getListPlayer().size();
                        game.getListPlayer().add(new StatistiqueJoueur(numPlayer, 0, 0, 0, namePlayer, "http://" + joueurHost + ":" + joueurPort));
                        System.out.println(numGame + " | " + numPlayer + " | " + namePlayer);
                        tab[0] = numGame;
                        tab[1] = numPlayer;
                        return tab;
                    } else {
                        noGame = true;
                    }
                }
                if (noGame) {
                    int numGame;
                    do {
                        numGame = new Random().nextInt(5000);

                    } while (listParti.get(numGame) != null);
                    int numPlayer = 0;
                    listParti.put(numGame, new Takenoko());
                    listParti.get(numGame).getListPlayer().add(new StatistiqueJoueur(numPlayer, 0, 0, 0, namePlayer, "http://" + joueurHost + ":" + joueurPort));
                    System.out.println("new Game");
                    System.out.println("ID partie | id joueur | nom joueur");
                    System.out.println(numGame + " | " + numPlayer + " | " + namePlayer);
                    tab[0] = numGame;
                    tab[1] = numPlayer;
                    return tab;
                }
            }
            return tab;
        }
    }

    @PostMapping(path = "/{id}/launch")
    public void launch(@PathVariable(value = "id") int id) {
        boolean start=false;
        synchronized (listParti) {
            if (listParti.get(id).getListPlayer().size() == nbjoueur && !listParti.get(id).getDejaLancer()) {
                listParti.get(id).setDejaLancer(true);
                start = true;
            }
        }
        if (start) {
            listParti.get(id).lancerParti(listParti.get(id).getListPlayer(), id);
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

    @PostMapping(value = "/{id}/{idJ}/FeuilleJoueurInitNbAction")
    public void feuilleJoueurInitNbAction(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().initNbAction();
    }

    @GetMapping(value = "/{id}/{idJ}/FeuilleJoueurGetNbAction")
    public int feuilleJoueurGetNbAction(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        return listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().getNbAction();
    }

    @GetMapping(value = "/{id}/{idJ}/FeuilleJoueurGetActionChoisie")
    public int feuilleJoueurGetActionChoisie(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        return listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().getActionChoisie();
    }

    @PostMapping(value = "/{id}/{idJ}/FeuilleJoueurSetActionChoisie")
    public void FeuilleJoueurSetActionChoisie(@RequestBody int actioneChoisie, @PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().setActionChoisie(actioneChoisie);
    }

    @GetMapping(value = "/{id}/{idJ}/FeuilleJoueurGetNbBambouRose")
    public int feuilleJoueurGetNbBambouRose(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        return listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().getNbBambouRose();
    }

    @GetMapping(value = "/{id}/{idJ}/FeuilleJoueurGetNbBambouVert")
    public int feuilleJoueurGetNbBambouVert(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        return listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().getNbBambouVert();
    }

    @GetMapping(value = "/{id}/{idJ}/FeuilleJoueurGetNbBambouJaune")
    public int feuilleJoueurGetNbBambouJaune(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        return listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().getNbBambouJaune();
    }

    @PostMapping(value = "/{id}/{idJ}/FeuilleJoueurDecNbACtion")
    public void feuilleJoueurDecNbACtion(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().decNbACtion();
    }

    @PostMapping(value = "/{id}/{idJ}/DeplacerPanda")
    public String deplacerPanda(@RequestBody Coordonnees coordonnees, @PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        try {
            listParti.get(id).getPanda().deplacerEntite(coordonnees, listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/{id}/{idJ}/DeplacerJardinier")
    public String deplacerJardinier(@RequestBody Coordonnees coordonnees, @PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        try {
            listParti.get(id).getJardinier().deplacerEntite(coordonnees, listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/{id}/{idJ}/PoserParcelle")
    public String poserParcelle(@RequestBody Parcelle parcelle, @PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        try {
            listParti.get(id).getTerrain().changements(parcelle, listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur());
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @PostMapping(value = "/{id}/{idJ}/PiocherUnObjectif")
    public String piocherUnObjectif(@RequestBody int i, @PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        try {
            listParti.get(id).getLesPiochesObjectif().piocherUnObjectif(listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur(), i);
            return "done";
        } catch (TricheException e) {
            return "can't";
        }
    }

    @GetMapping(value = "/{id}/{idJ}/FeuilleJoueurGetMainObjectif")
    public ArrayList<CartesObjectifs> feuilleJoueurGetMainObjectif(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        return listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur().getMainObjectif();
    }

    @PostMapping(value = "/{id}/{idJ}/VerifObjectifAccompli")
    public void verifObjectifAccompli(@PathVariable(value = "id") int id, @PathVariable(value = "idJ") int idJoueur) {
        listParti.get(id).getTerrain().verifObjectifAccompli(listParti.get(id).getListPlayer().get(idJoueur).getFeuilleJoueur());
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

