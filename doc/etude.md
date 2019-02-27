ressources :  
- Parcelles
- Coordonnees
- CartesObjectifs(panda,jardinier,parcelle)

Interface : 
- rest controller (serveur)
- rest controller (client) + un service (client)


Scénario de jeu:
 
-le serveur attend 1ou plusieurs joueurs(reglable) 
-Le bot se presente au serveur 
-le serveur lance la parti

jusqu'a la fin { 
    -le serveur autorise le bot à jouer son tour
    -le bot demande les informations neccessaire au serveur
    -le serveur donne les informations
    -le bot joue 
}
-le serveur averti que la partie est fini
