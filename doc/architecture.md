            ************************* DECOUPAGE ********************************
      
      
 Après réflexion nous avons répertorié toutes les classes et packages qui étaient dépendant les uns des autres,
  ainsi nous avons pu déjà mettre en place un sous-projet « commun » qui ne contient pas de classe Application.java.
  
 Pour les autres sous-projets, « serveur » et « joueur », nous avons décidé de les implémenter tous 
 les deux comme des serveurs (ce sont tout les deux des SpringApplication). Ainsi la communication entre le joueur et le serveur peut s’effectuer dans les deux 
 sens. 
 Au niveau des classes, le joueur ne contient que la classe IAPanda (le joueur), et les configurations nécessaires à sa
 fonction de serveur.
 Le serveur quant à lui contient toutes les autres classes relatives au moteur de jeu, et les configurations 
 nécessaires à sa fonction de serveur.
     
      
      
      
      ************************* ECHANGES SERVEUR-JOUEUR ********************************

1/ LES ROUTES IMPLEMENTEES

La communication joueur serveur est implémentée selon la figure ci-dessous :
 
![routes](images/routes.png)
   
SERVEUR:

	Etapes 1 et 2 de la Figure: échanges serveur-joueur :

Tout d’abord, nous permettons la connexion du joueur au serveur via l’url :
            
            {ipHostServeur}:8080/{Pseudo}/connect
            
Celle-ci renvoie un tableau de deux entiers :

•	le 1er :  l’identifiant de la partie 

•	le 2ème : l’identifiant du joueur voulant entrer dans la partie

	Etape 5 de la Figure: échanges serveur-joueur :

Les informations du jeu sont obtenues via l’url :

    {ipHostServeur}:8080/{identifiantPartie}/{OPERATION}

•	Opérations en GET : 


![routes](images/route1.png)

•	Opérations en POST : 

![routes](images/route2.png)

 
Les actions du jeu sont obtenues via l’url :

    {ipHostServeur}:8080/{identifiantPartie}/{identifiantJoueur}/{OPERATION}

•	Opérations en GET : 

![routes](images/route3.png)


•	Opérations en POST : 

![routes](images/route4.png)



2/ LANCEMENT DE PARTIE

JOUEUR 

	Etape 3 de la Figure:

Pour :

•	Créer un nouveau joueur

•	Connecter ce joueur au serveur

•	Demander de lancer la partie correspondante à l’identifiant ce nouveau joueur

On utilise l’url suivante :

    {ipHostJoueur}:JoueurPort }/newPlayer

	Etape 4 de la Figure 5 : échanges serveur-joueur :

Pour :

•	Chercher un joueur dans la liste des joueurs connectés

•	Le faire joueur

On utilise l’url suivante :

    {ipHostJoueur}:JoueurPort }/{identifiantPartie}/{identifiantJoueur}/joue

