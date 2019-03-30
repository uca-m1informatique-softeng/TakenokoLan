Feature: demander les deplacements possibles du Panda

  Scenario: le client appelle GET /PandaGetDeplacementsPossible
    Given une partie
    When le client appelle /PandaGetDeplacementsPossible
    Then le client reçoit status code 200
    And le client reçoit une liste vide

