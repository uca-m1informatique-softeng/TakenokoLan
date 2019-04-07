Feature: verifier qu'un objectif est accompli

  Background:
    Given une nouvelle partie

  Scenario: le client appelle veut verifier qu'un objectif est validé
    When le client appelle /VerifObjectifAccompli
    Then le nombre de points du joueur est non null