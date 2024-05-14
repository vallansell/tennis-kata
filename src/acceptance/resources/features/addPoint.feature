@AddPoint
Feature: Add a point to a player
  As a user, I want to add a point to a player

  Scenario Outline: The user adds a point to a player
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "<player>"
    Then the score should be "Player A: <scoreA> / Player B: <scoreB>" and the game should be "IN_PROGRESS"

    Examples:
      | player | scoreA | scoreB |
      | A      | 15     | 0      |
      | B      | 0      | 15     |

  Scenario Outline: The user adds two points to a player
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    Then the score should be "Player A: <scoreA> / Player B: <scoreB>" and the game should be "IN_PROGRESS"

    Examples:
      | player | scoreA | scoreB |
      | A      | 30     | 0      |
      | B      | 0      | 30     |

  Scenario Outline: The user adds points to a player to win the game
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    Then the score should be "Player <player> wins the game" and the game should be "FINISHED"

    Examples:
      | player |
      | A      |
      | B      |

  Scenario: The user adds 3 points to both players and reach deuce
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "A"
    And the user adds a point to player "A"
    And the user adds a point to player "A"
    And the user adds a point to player "B"
    And the user adds a point to player "B"
    And the user adds a point to player "B"
    Then the score should be "Deuce" and the game should be "IN_PROGRESS"

  Scenario: The user gives advantage to the first player, then gives advantage to the other player who finally wins the game
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "A"
    And the user adds a point to player "A"
    And the user adds a point to player "A"
    And the user adds a point to player "B"
    And the user adds a point to player "B"
    And the user adds a point to player "B"
    And the user adds a point to player "A"
    Then the score should be "Advantage A" and the game should be "IN_PROGRESS"
    When the user adds a point to player "B"
    Then the score should be "Advantage B" and the game should be "IN_PROGRESS"
    When the user adds a point to player "B"
    Then the score should be "Player B wins the game" and the game should be "FINISHED"

  Scenario: The user cannot add a point because the player is unknown
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "C"
    Then the point addition should fail because the player "C" is unknown

  Scenario Outline: The user cannot add a point because the game is already finished
    Given a new tennis game has started with player A and player B
    When the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    And the user adds a point to player "<player>"
    Then the score should be "Player <player> wins the game" and the game should be "FINISHED"
    And the user adds a point to player "<player>"
    Then the point addition should fail because the game is finished

    Examples:
      | player |
      | A      |
      | B      |
