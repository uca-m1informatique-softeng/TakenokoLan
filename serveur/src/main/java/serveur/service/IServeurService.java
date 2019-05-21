package serveur.service;

public interface IServeurService {

    void JoueClient(String urlJoueur,int id, int idPlayer);

    void finPartie(String urlJoueur,int id);
}
