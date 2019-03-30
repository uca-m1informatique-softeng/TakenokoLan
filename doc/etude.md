commun.ressources :  
- Parcelles
- Coordonnees
- CartesObjectifs(panda,jardinier,parcelle)

Interface : 
- rest serveur.controller (joueur.serveur)
- rest serveur.controller (client) + un joueur.service (client)


Scénario de jeu:

- le joueur.serveur attend 1ou plusieurs joueurs(reglable)
- le bot se presente au joueur.serveur 
- le joueur.serveur lance la parti

jusqu'a la fin{
- le joueur.serveur autorise le bot à jouer son tour
- le bot demande les informations neccessaire au joueur.serveur
- le joueur.serveur donne les informations
- le bot joue 
}

- le joueur.serveur averti que la partie est fini
