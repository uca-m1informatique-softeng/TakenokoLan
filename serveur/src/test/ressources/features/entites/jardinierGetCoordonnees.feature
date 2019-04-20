Feature: demander les coordonnees du Jardinier

  Scenario: le client appelle GET /JardinierGetCoordonnees
    Given one partie
    When le client appelle /JardinierGetCoordonnees
    Then client reçoit le status code 200
    And le client reçoit les coordonnees du jardinier

