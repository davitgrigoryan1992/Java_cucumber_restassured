Feature: Demo Java automation framework using Gorest APIs

  @scenario1 @user
  Scenario: Should GET users list and store in Database
    Given gorest.co.in server is working
    When we run GET users list request
    Then assert that response status code is 200
    And assert that there are 20 users data in response
    And we store all Users data in MySql database
    And assert that 'id, name, email, gender, status, created_at, updated_at' data is shown for all existing users
    And we remove all stored data from database
