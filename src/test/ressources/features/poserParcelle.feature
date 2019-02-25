Feature: Poser une parcelle

  Background :
    Given un terrain
    And une pioche de parcelles

  Scenario: le bot pioche une parcelle et la pose
    When la parcelle est posée
    Then  le nombre de parcelles sur le terrain augmente du nombre de parcelles posees

