Feature: Demo Java automation framework using Gorest APIs

  # Happy scenario
  @scenario1 @user
  Scenario: Should GET users list and store in Database
    Given gorest.co.in server is working. Scenario N1
    And we enter valid access token for Authorization
    When we run GET users list request
    Then assert that response status code is 200 for Get user request
    And assert that there are 20 users data in response
    And we store all Users data in MySql database
    And assert that 'id, name, email, gender, status, created_at, updated_at' data is shown for all existing users
    And we remove all stored data from database

  # Happy scenario
  @scenario2 @user
  Scenario: Should POST a new user in users list. Should Delete new created user.
    Given gorest.co.in server is working. Scenario N2
    And we provide following user details: Name - "Poxos Poxosyan", Mail - "poxos@example.com", Gender - "Male", Status - "Active"
    And we enter valid access token for Authorization
    When we run POST new user request
    And assert that response status code is 201 for Post user request
    Then assert that the Location header contains the URL pointing to the newly created resource
    And we run GET new created user request
    And assert that response status code is 200 for Get new user request
    And assert that created user has details: Name - "Poxos Poxosyan", Mail - "poxos@example.com", Gender - "Male", Status - "Active"
    And we remove new created user
    And assert that response status code is 204 for delete user request
    And we assert that user is no longer exist

  # Happy scenario
  @scenario3 @user
  Scenario: Should update user name in users list
    Given gorest.co.in server is working. Scenario N3
    And we enter valid access token for Authorization
    And user with name "Test" is created in users list
    And we enter "NewTest" name for PATCH user request
    When we run PATCH user request
    Then assert that response status code is 200 for PATCH user request
    And we run GET new created user request
    And assert that user name has updated to "NewTest"
    And we remove new created user
    And assert that response status code is 204 for delete user request

  # Happy scenario
  @scenario4 @user
  Scenario: Should update user status as inactive
    Given gorest.co.in server is working. Scenario N4
    And we enter valid access token for Authorization
    And user with name "Test" is created in users list
    And we enter user's "Inactive" status for PATCH user request
    When we run PATCH user request
    Then assert that response status code is 200 for PATCH user request
    And we run GET new created user request
    Then assert that user status has updated to "Inactive"
    And we remove new created user
    And assert that response status code is 204 for delete user request

  # Happy scenario
  @scenario5 @user
  Scenario: Should update user all details with one request
    Given gorest.co.in server is working. Scenario N5
    And we provide following user details: Name - "Poxos Poxosyan", Mail - "poxos@example.com", Gender - "Male", Status - "Active"
    And we enter valid access token for Authorization
    When we run POST new user request
    And assert that response status code is 201 for Post user request
    Then assert that the Location header contains the URL pointing to the newly created resource
    And we update existing user details to: Name - "Poxosuhi Poxosyan", Mail - "poxosuhi@example.com", Gender - "Female", Status - "Inactive"
    When we run PUT user request
    Then assert that response status code is 200 for PUT user request
    And we run GET new created user request
    Then assert that user status has updated to "Inactive"
    And assert that user name has updated to "Poxosuhi Poxosyan"
    And we remove new created user
    And assert that response status code is 204 for delete user request

  # Unhappy scenario
  @scenario6 @user
  Scenario: Should not POST a new user using invalid Authorization
    Given gorest.co.in server is working. Scenario N6
    And we provide following user details: Name - "Poxos Poxosyan", Mail - "poxos@example.com", Gender - "Male", Status - "Active"
    And we enter invalid access token for Authorization
    When we run POST new user request
    Then assert that response status code is 401 for Post user request
    And assert that error message is "" "Authentication failed"

  # Unhappy scenario
  @scenario7 @user
  Scenario: Should not POST a new user with invalid email address
    Given gorest.co.in server is working. Scenario N7
    And we provide following user details: Name - "Poxos Poxosyan", Mail - "poxos.com", Gender - "Male", Status - "Active"
    And we enter valid access token for Authorization
    When we run POST new user request
    Then assert that response status code is 422 for Post user request
    And assert that error message is "email" "is invalid"

  # Unhappy scenario
  @scenario8 @user
  Scenario: Shouldn't Post new user using invalid status
    Given gorest.co.in server is working. Scenario N8
    And we provide following user details: Name - "Test Test", Mail - "test@example.com", Gender - "Male", Status - "Invalid_status"
    And we enter valid access token for Authorization
    When we run POST new user request
    Then assert that response status code is 422 for Post user request
    And assert that error message is "status" "can be Active or Inactive"

  # Unhappy scenario
  @scenario9 @user
  Scenario: Shouldn't Post new user using invalid gender
    Given gorest.co.in server is working. Scenario N9
    And we provide following user details: Name - "Test Test", Mail - "test@example.com", Gender - "not_oriented", Status - "Inactive"
    And we enter valid access token for Authorization
    When we run POST new user request
    Then assert that response status code is 422 for Post user request
    And assert that error message is "gender" "can be Male or Female"

  # Unhappy scenario
  @scenario10 @user
  Scenario: Shouldn't Post new user using invalid body parameter
    Given gorest.co.in server is working. Scenario N10
    And we provide following user details: Name - "Test Test", Mail - "test@example.com", Gender - "Female", Status - "Inactive"
    And we provide extra parameter in the body
    And we enter valid access token for Authorization
    When we run POST new user request
    Then assert that response status code is 400 for Post user request
    And assert that error message is "" "extra parameter exists in request body"

  # Unhappy scenario
  @scenario11 @user
  Scenario: Should not GET users list having wrong Content-Type
    Given gorest.co.in server is working. Scenario N11
    And we enter "invalid/content_type" as Content-Type
    And we enter valid access token for Authorization
    When we run GET users list request
    Then assert that response status code is 415 for Get user request

  # Unhappy scenario
  @scenario12 @user
  Scenario: Shouldn't Get a new user details using wrong userId
    Given gorest.co.in server is working. Scenario N12
    And we provide following user details: Name - "Test Test", Mail - "test@example.com", Gender - "Male", Status - "Active"
    And we enter valid access token for Authorization
    When we run POST new user request
    And assert that response status code is 201 for Post user request
    Then assert that the Location header contains the URL pointing to the newly created resource
    And we run GET new created user request with invalid userId
    And assert that response status code is 404 for Get new user request
    And assert that error message is "Resource not found" for Get request
