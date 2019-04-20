Feature: effectuer les actions

  Background:
    Given la partie
    And des parcelles

  Scenario: le client joue sur le terrain
    When le client appelle /GetListeZonesPosables
    Then le client recoit statut code 200
    When le client appelle/PoserParcelle
    Then client recoit statut code 200 and done
    When client appelle /GetZoneJouee
    Then client recoit statut code 200
    Then une parcelle est posée sur le terrain

  Scenario: le client appelle /DeplacerJardinier
    When le client appelle /DeplacerJardinier
    Then si c'est possible le jardinier est déplacé sur le terrain

  Scenario: le client appelle /DeplacerPanda
      When le client appelle /DeplacerPanda
      Then si c'est possible le panda est déplacé sur le terrain


  Scenario: le client pioche un objectif
    When le client appelle /PiocheUnObjectif
    Then  return au moins une carte


