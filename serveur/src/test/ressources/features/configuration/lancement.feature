Feature: Lancer une partie

  Background :
    Given le terrain
    And un bot

  Scenario: une partie est lancee
    When la partie est en cours
    Then on affiche hello

