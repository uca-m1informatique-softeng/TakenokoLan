Feature: Joueur demande combien de bambous il a

  Background :
    Given un terrain vide

  Scenario: le joueur appelle getBambouJaune
  Given une parcelle jaune
    And un panda qui mange le bambou jaune
    When joueur appelle getBambouJaune
    Then reçoit un statu codes 200
    Then il y a un seul bambou jaune

  Scenario: le joueur appelle getBambouVert
   Given une parcelle vert
      And un panda qui mange le bambou vert
      When  joueur appelle getBambouVert
      Then reçoit un status codes 200
      Then il y a un seul bambou vert

  Scenario: le joueur appelle getBambouRose
   Given une parcelle rose
      And un panda qui mange le bambou rose
      When joueur appelle getBambouRose
      Then reçoit statu code 200
      Then il y a un seul bambou rose