package gorest.co.in.integration.users;
import gorest.co.in.integration.helper.ApiForUsers;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;

public class UsersStepDefinitions {

    private ApiForUsers helper = null;

    public UsersStepDefinitions(ApiForUsers helper) {
        this.helper = helper;
    }

    @Given("there are some users")
    public void there_are_some_users() {
        System.out.println("test");
    }

//    @And("we receive users list")
//    public void we_receive_users_list() {
//
//      '{"name":"Tenali Ramakrishna", "gender":"Male", "email":"tenali.ramakrishna@15ce.com", "status":"Active"}
//
//        initConsentResponse = helper.createUser();
//
//    }

    @And("we request all users list")
    public void we_receive_all_users_list() {
        helper.getUsersListResponse = helper.getUsersList();
        Assert.assertNotNull("There is no any user!",
                helper.getUsersListResponse);
    }

    @And("^status code is (\\d+)$")
    public void status_code_is(int statusCode) {
        Assert.assertEquals(
                "Expected status code is:" + statusCode + " for get user List. But Response is "
                        + helper.getUsersListResponse.getBody().asString(),
                statusCode, helper.getUsersListResponse.getStatusCode());

    }

}
