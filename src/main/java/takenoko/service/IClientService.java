package takenoko.service;

import takenoko.ressources.Parcelle;
import takenoko.utilitaires.Coordonnees;

import java.util.ArrayList;

public interface IClientService {
    ArrayList<Parcelle> piocher();

    void reposeSousLaPioche(ArrayList<Parcelle> aRemettre);

    public Boolean piocheParcelleIsEmpty();

    ArrayList<Coordonnees> pandaGetDeplacementsPossible();
}
