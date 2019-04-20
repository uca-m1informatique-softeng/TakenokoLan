Feature: demander les deplacements possibles du Jardinier

  Scenario: le client appelle GET /JardinierGetDeplacementsPossible
    Given on init une partie
    When le client appelle /JardinierGetDeplacementsPossible
    Then le client reçoit un status code 200
    And le client reçoit liste vide
