package gorest.co.in.integration.users;
import gorest.co.in.integration.helper.ApiForUsers;
import gorest.co.in.integration.helper.StoreTestDataMySQL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;

import java.util.List;

public class UsersStepDefinitions {

    private ApiForUsers helper;
    StoreTestDataMySQL storeData;

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

    @And("^assert that response status code is (\\d+)$")
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
        Assert.assertEquals("Returned Users ID count is wrong! - Returned User ID count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.id").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.id").size(), 20);
        Assert.assertEquals("Returned Users name count is wrong! - Returned User Name count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.name").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.name").size(), 20);
        Assert.assertEquals("Returned Users email count is wrong! - Returned User email count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.email").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.email").size(), 20);
        Assert.assertEquals("Returned Users gender count is wrong! - Returned User gender count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.gender").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.gender").size(), 20);
        Assert.assertEquals("Returned Users status count is wrong! - Returned User status count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.status").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.status").size(), 20);
        Assert.assertEquals("Returned Users created_at count is wrong! - Returned User created_at count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.created_at").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.created_at").size(), 20);
        Assert.assertEquals("Returned Users updated_at count is wrong! - Returned User updated_at count is "
                        + helper.getUsersListResponse.getBody().jsonPath().getList("data.updated_at").size() + ", but should be " + 20,
                helper.getUsersListResponse.getBody().jsonPath().getList("data.updated_at").size(), 20);
    }
}
