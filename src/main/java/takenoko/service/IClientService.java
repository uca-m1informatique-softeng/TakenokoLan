package takenoko.service;

import takenoko.ressources.Parcelle;

import java.util.ArrayList;

public interface IClientService {
    ArrayList<Parcelle> piocher();

    void reposeSousLaPioche(ArrayList<Parcelle> aRemettre);
}
