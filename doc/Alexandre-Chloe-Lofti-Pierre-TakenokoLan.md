# Feedback de la première livraison

Groupe https://github.com/orgs/uca-m1informatique-softeng/teams/kaledoteam


## Intégration continue ##
C’est bien opérationnel : https://travis-ci.com/uca-m1informatique-softeng/TakenokoLan


## Gestion de projet ##
Vous avez repris le système d'issues, mais sans faire de kanban.


## TDD appliqué à Takenoko ##
Au moment de la livraison, l'intégration de Cucumber n'était pas opérationnel (non visible sur la trace associée au tag, https://travis-ci.com/uca-m1informatique-softeng/TakenokoLan/builds/102540380)


Depuis, l'intégration est maintenant opérationnel, avec un effort notable sur la transformation de tests en BDD (11 scénarii en tout).


## Etude d’architecture sur le découpage en services REST ##
Vous n'avez pas détaillé la conception du découpage, mais vous avez implémenté le joueur.serveur (Takenoko/Controller) comme un web joueur.service, en exposant ses méthodes. Une première remarque, trop de méthodes sont exposées : par exemple, "ReposeSousLaPioche", c'est interne au joueur.serveur (le joueur choisit une tuile, dit laquelle, le joueur.serveur s'occupe du reste). Autre exemple : "FeuilleJoueurGetActionChoisie" : le client demande au joueur.serveur quelle action il a choisi... le joueur devrait mémoriser les coups d'un tour. De plus, vous retournez une FeuilleJoueur puis vous exposez des méthodes pour accéder à des propriétés de cette FeuilleJoueur (nombre de bambous d'une couleur par exemple).


Il y a donc un travail à poursuivre dans le choix des méthodes à exposer via le web joueur.service.


Dans la réalisation, le joueur (il n'y a qu'un joueur car dans le mapping de @RequestMapping(path = "/launch") il n'y a qu'un joueur de créer et aussi parce que certaines méthodes retournent directement sur la base du joueur 0 comme  "return takenoko.getListPlayer().get(0).getFeuilleJoueur().getNbBambouVert();") passe par le web joueur.service pour échanger avec le joueur.serveur (le joueur.serveur appel directement l'objet Joueur, ce qui est normal pour un début).


La partie se lance en faisant une requete : http://localhost:8080/launch (d'ailleurs, à quoi sert init ?). Proposze une exécusion "automatique".


Pour en revevir à l'étude du découpage, c'est insuffisant dans la description (vous êtes allé plus loin dans la réalisation). Vous avez identifié deux web services (client et joueur.serveur), mais un seul "consommateur" (le client). Vous aurez aussi besoin de la liaison dans l'autre sens. Dans les commun.ressources, il y a bien des choses échangées, plus que des parcelles, des coordonnées et des objectifs, puisque vous retournez aussi des FeuilleJoueur.


Il est conseillé d'expliciter l'ensemble des routes/échanges (dans les deux sens) dans le scénario.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE2OTgwMzEyNjhdfQ==
-->