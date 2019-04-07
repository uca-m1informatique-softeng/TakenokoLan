Feature: verifier qu'un objectif est accompli

  Background:
    Given une nouvelle partie

  Scenario: le client appelle veut verifier qu'un objectif est validé
    When le client appelle /VerifObjectifAccompli
    Then le nombre de points du joueur est non null



  Scenario: le client appelle veut verifier que sa main de carte objectif diminue du nombre de carte validée
    When le client appelle /FeuilleJoueurGetMainObjectif
    Then le nombre de carte objectif dans la main est different