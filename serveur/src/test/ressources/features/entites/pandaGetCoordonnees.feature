Feature: demander les coordonnees du Panda

  Scenario: le client appelle GET /PandaGetCoordonnees
    Given besoin d'une partie
    When le client appelle /PandaGetCoordonnees
    Then le client reçoit le status code 200
    And le client reçoit les coordonnees du panda

