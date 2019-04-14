Les routes créées: 

/init ::  retourne "init done"
 
/Connect ::  retourne tableau

/launch ::  lance la partie
   
/GetZoneJouee ::  retourne liste de zones jouées (GET) 
  
/Piocher :: retourrne la liste des parcelles piochées (GET)
    
/ReposeSousLaPioche :: repose sous la pioche (POST)

/PiocheParcelleIsEmpty :: retourne pioche parcelle vide (GET)

/PandaGetDeplacementsPossible :: retourne déplacements du panda possible (GET)

PiochePandaIsEmpty :: retourne pioche panda vide (GET)

/JardinierGetDeplacementsPossible :: retourne déplacements du jardinier possible (GET)


/GetFeuilleJoueur :: retourne la feuille joueur (GET)

/FeuilleJoueurInitNbAction :: init le nombre d'action du joueur dans feuille joueur (POST)

/FeuilleJoueurGetNbAction :: retourne le nombre d'action du joueur dans feuille joueur (GET)

/FeuilleJoueurGetActionChoisie :: retoune l'action choisie par le joueur (GET)

/FeuilleJoueurSetActionChoisie :: impelmente l'action choisie par le joueur (POST)

/FeuilleJoueurGetNbBambouRose ; /FeuilleJoueurGetNbBambouVert ; /FeuilleJoueurGetNbBambouJaune :: retourne le nombre
de bambous en fonction de la couleur (GET)

/FeuilleJoueurDecNbACtion :: decrémente le nombre d'action du joueur (POST)

/DeplacerPanda :: deplace le panda (POST)

/DeplacerJardinier :: deplace le jardinier (POST)

/PoserParcelle :: pose une parcelle (POST)

/PiocherUnObjectif :: pioche un objectif (POST)

 /FeuilleJoueurGetMainObjectif :: retourne la main objectif du joueur (GET)

/VerifObjectifAccompli :: vérifie qu'un objectif est accompli (POST)

/PandaGetCoordonnees ; /JardinierGetCoordonnees :: retourne les coordonnées des entités (GET)

/GetListeZonesPosables :: retoune la liste des zones de parcelles posables (GET)