Feature: demander si la pioche panda est vide

  Scenario: le client appelle GET /PiochePandaIsEmpty
    Given on init une partie
    When le client appelle /PiochePandaIsEmpty
    Then le client reçoit un status code 200
    And le client reçoit faux car la pioche est pleine
