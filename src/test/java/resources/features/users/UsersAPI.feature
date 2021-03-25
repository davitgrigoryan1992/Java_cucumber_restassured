Feature: Gorest APIs

  @user
  Scenario: Get all users list
    Given there are some users
    When we request all users list
    Then status code is 200
