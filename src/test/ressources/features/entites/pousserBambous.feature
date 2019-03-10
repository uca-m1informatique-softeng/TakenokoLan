Feature: Faire pousser un bambou

  Background :
    Given un terrain avec des parcelles irriguées
    And un jardinier

  Scenario: un bambou par defaut
    When on pose d'une parcelle
    Then il y a un seul bambou

  Scenario: le jardinier fait pousser un bambou
    When le jardinier se déplace sur une parcelle
    Then le nombre de bambous augmente

