Feature: damander si la pioche parcelle est vide

  Scenario: le client appelle GET /PiocheParcelleIsEmpty
    Given une partie
    When le client appelle /PiocheParcelleIsEmpty
    Then le client reçoit status code 200
    And le client reçoit faux

  Scenario: le client appelle GET /PiocheParcelleIsEmpty alors que la pioche est vide
    Given la pioche est vide
    When le client appelle /PiocheParcelleIsEmpty alors que la pioche est vide
    Then le client reçoit vrai