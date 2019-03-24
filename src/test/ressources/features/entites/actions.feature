Feature: effectuer les actions

  Background:
    Given la partie
    And le jardinier
    And le panda
    And un terrain avec des parcelles



  Scenario: le client appelle/PoserParcelle
    When le client appelle/PoserParcelle
    Then si c'est possible une parcelle est posée sur le terrain

  Scenario: le client appelle /DeplacerJardinier
    When le client appelle /DeplacerJardinier
    Then si c'est possible le jardinier est déplacé sur le terrain

  Scenario: le client appelle /DeplacerPanda
      When le client appelle /DeplacerPanda
      Then si c'est possible le panda est déplacé sur le terrain


