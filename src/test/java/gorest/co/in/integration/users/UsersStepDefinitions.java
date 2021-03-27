package gorest.co.in.integration.users;
import gorest.co.in.integration.helper.ApiForUsers;
import gorest.co.in.integration.helper.StoreTestDataMySQL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.List;
import java.util.Random;

public class UsersStepDefinitions {

    private ApiForUsers helper;
    StoreTestDataMySQL storeData;
    private String newUserId;

    public UsersStepDefinitions(ApiForUsers helper) {
        this.helper = helper;
    }

    @Given("^gorest.co.in server is working$")
    public void server_is_working() {
        System.out.println("We hope gorest.co.in server is working! And Test is starting......");
    }

    @And("^we run GET users list request$")
    public void we_receive_all_users_list() {
        helper.getUsersListResponse = helper.getUsersList();
        Assert.assertNotNull("There is no any user!",
                helper.getUsersListResponse);
    }

    @And("^assert that response status code is (\\d+) for Get user request$")
    public void status_code_is(int statusCode) {
        Assert.assertEquals(
                "Expected status code is:" + statusCode + " for get user List. But Response is "
                        + helper.getUsersListResponse.getBody().asString(),
                statusCode, helper.getUsersListResponse.getStatusCode());
    }

    @And("^we store all Users data in MySql database$")
    public void we_store_users_data_in_mysql_database() {
       storeData.storeUserDateInDatabase(helper.getUsersListResponse);
    }

    @And("^we remove all stored data from database$")
    public void we_remove_stored_data_from_database() {
        storeData.removeStoredDataFromDatabase();
    }

    @And("^assert that there are (\\d+) users data in response$")
    public void assert_that_all_existing_users_are_shown(int arg) {
        Assert.assertTrue("Test Error: Get Users list response returned an error!",
                helper.getUsersListResponse.getStatusCode() == 200);

        List<Object> usersList = helper.getUsersListResponse.getBody().jsonPath().getList("data");
        Assert.assertEquals("Returned Users count is wrong! - Returned User count is " + usersList.size() + ", but should be " + arg,
                arg, usersList.size());
    }

    @And("^assert that 'id, name, email, gender, status, created_at, updated_at' data is shown for all existing users$")
    public void assert_that_all_data_are_shown_for_all_users() {
        int limit = 20;
        Assert.assertEquals("Returned Users ID count is wrong! - Returned User ID count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.id").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.id").size(), limit);
        Assert.assertEquals("Returned Users name count is wrong! - Returned User Name count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.name").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.name").size(), limit);
        Assert.assertEquals("Returned Users email count is wrong! - Returned User email count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.email").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.email").size(), limit);
        Assert.assertEquals("Returned Users gender count is wrong! - Returned User gender count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.gender").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.gender").size(), limit);
        Assert.assertEquals("Returned Users status count is wrong! - Returned User status count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.status").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.status").size(), limit);
        Assert.assertEquals("Returned Users created_at count is wrong! - Returned User created_at count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.created_at").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.created_at").size(), limit);
        Assert.assertEquals("Returned Users updated_at count is wrong! - Returned User updated_at count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.updated_at").size() + ", but should be " + limit,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.updated_at").size(), limit);
    }

    @And("^we provide following user details: Name - \"([^\"]*)\", Mail - \"([^\"]*)\", Gender - \"([^\"]*)\", Status - \"([^\"]*)\"$")
    public void we_provide_following_user_details_request_body(String name, String mail, String gender, String status) {
        helper.postCreateUserRequest = new JSONObject();
        helper.postCreateUserRequest.put("name", name);
        helper.postCreateUserRequest.put("email", getRandomName()+mail);
        helper.postCreateUserRequest.put("gender", gender);
        helper.postCreateUserRequest.put("status", status);
    }

    @And("^we enter valid access token for Authorization$")
    public void we_enter_valid_access_token_for_Authorization() {
        helper.authorizationBearer = "Bearer 7d35e09d63a2d4631eadbc118350c1482ad87390960fa1e965c0bea0022d58e1";
    }

    @When("^we run POST new user request$")
    public void we_run_POST_new_user_request() {
        helper.postCreateUserResponse = helper.postNewUser(helper.postCreateUserRequest.toString());
    }

    @And("^assert that response status code is (\\d+) for Post user request$")
    public void status_code_is_for_post_user(int statusCode) {
        Assert.assertEquals(
                "Expected status code is:" + statusCode + " for Post user request. But Response is "
                        + helper.postCreateUserResponse.getBody().asString(),
                statusCode, helper.postCreateUserResponse.getStatusCode());
    }

    @And("^assert that the Location header contains the URL pointing to the newly created resource$")
    public void assert_that_location_header_contains_the_url() {
        if(helper.postCreateUserResponse.getHeader("Location") != null ) {
            Assert.assertTrue("Post Users response Location is wrong!!!",
                    helper.postCreateUserResponse.getHeader("Location").startsWith(helper.baseUrl));
            newUserId = helper.postCreateUserResponse.getBody().jsonPath().get("data.id").toString();
            Assert.assertTrue("Post Users response Location doesn't contain new user's ID",
                    helper.postCreateUserResponse.getHeader("Location").contains(newUserId));
        } else
            Assert.fail("Post Users Response Location is null!");
    }

    @And("^we run GET new created user request$")
    public void we_run_GET_new_created_user_request() {
        helper.getNewUserDetailsResponse = helper.getNewUserDetails(newUserId);
    }

    @And("^assert that response status code is (\\d+) for Get new user request$")
    public void status_code_is_for_get_new_user(int statusCode) {
        Assert.assertEquals(
                "Expected status code is:" + statusCode + " for Get New user request. But Response is "
                        + helper.getNewUserDetailsResponse.getBody().asString(),
                statusCode, helper.getNewUserDetailsResponse.getStatusCode());
    }

    @Then("^assert that created user has details: Name - \"([^\"]*)\", Mail - \"([^\"]*)\", Gender - \"([^\"]*)\", Status - \"([^\"]*)\"$")
    public void assert_that_created_user_has_details(String name, String mail, String gender, String status) {
        Assert.assertEquals("Returned new Users Id is wrong! - Returned new User Id is "
                        + helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.id") + ", but should be " + newUserId,
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.id").toString(), newUserId);
        Assert.assertEquals("Returned new Users name is wrong! - Returned new User name is "
                        + helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.name") + ", but should be " + name,
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.name"), name);
        Assert.assertTrue("Returned new Users mail is wrong! - Returned new User mail is "
                        + helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.email") + ", but should be " + mail,
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.email").toString().contains(mail));
        Assert.assertEquals("Returned new Users gender is wrong! - Returned new User gender is "
                        + helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.gender") + ", but should be " + gender,
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.gender"), gender);
        Assert.assertEquals("Returned new Users status is wrong! - Returned new User status is "
                        + helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.status") + ", but should be " + status,
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.status"), status);
        Assert.assertNotNull("Returned new Users details created_at parameter is missing!!!",
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.created_at"));
        Assert.assertNotNull("Returned new Users details updated_at parameter is missing!!!",
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.updated_at"));
    }

    @Then("^we remove new created user$")
    public void we_remove_new_created_user() {
        helper.deleteNewUserResponse = helper.deleteNewUser(newUserId);
    }

    @And("^assert that response status code is (\\d+) for delete user request$")
    public void status_code_is_for_delete_user(int statusCode) {
        Assert.assertEquals(
                "Expected status code is:" + statusCode + " for Delete New user request. But Response is "
                        + helper.deleteNewUserResponse.getBody().asString(),
                statusCode, helper.deleteNewUserResponse.getStatusCode());
    }

    @And("^we assert that user is no longer exist$")
    public void we_assert_that_user_is_no_longer_exist() {
        helper.getNewUserDetailsResponse = helper.getNewUserDetails(newUserId);

        Assert.assertEquals("User is not deleted!!!",
                helper.getNewUserDetailsResponse.getBody().jsonPath().get("data.message"), "Resource not found");
    }

    private String getRandomName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String rndName = salt.toString();
        return rndName;
    }
}
