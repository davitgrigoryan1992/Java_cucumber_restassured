package gorest.co.in.integration.helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

public class ApiForUsers {

    public Response getUsersListResponse = null;
    public JSONObject postCreateUserRequest = null;
    public JSONObject patchUserRequest = null;
    public JSONObject putUserRequest = null;
    public Response patchUserResponse = null;
    public Response putUserResponse = null;
    public static String authorizationBearer = null;
    public Response postCreateUserResponse = null;
    public Response getNewUserDetailsResponse = null;
    public Response deleteNewUserResponse = null;
    public String baseUrl = "https://gorest.co.in/public-api/";
    public String contentType = "application/json";

    public Response postNewUser(String messageBody) {
        return RestAssured.given().log().all().contentType(contentType).accept("application/json")
                .header("Authorization", authorizationBearer)
                .body(messageBody).post(baseUrl + "users").then().log().all().extract().response();
    }

    public Response getUsersList() {
        return RestAssured.given().log().all().contentType(contentType).accept("application/json")
                .header("Authorization", authorizationBearer)
                .get(baseUrl + "users").then().log().all().extract().response();
    }

    public Response getNewUserDetails(String userId) {
        return RestAssured.given().log().all().contentType(contentType).accept("application/json")
                .header("Authorization", authorizationBearer)
                .get(baseUrl + "users/" + userId).then().log().all().extract().response();
    }

    public Response deleteNewUser(String userId) {
        return RestAssured.given().log().all().contentType(contentType).accept("application/json")
                .header("Authorization", authorizationBearer)
                .delete(baseUrl + "users/" + userId).then().log().all().extract().response();
    }

    public Response patchUser(String messageBody, String userId) {
        return RestAssured.given().log().all().contentType(contentType).accept("application/json")
                .header("Authorization", authorizationBearer)
                .body(messageBody).patch(baseUrl + "users/" + userId).then().log().all().extract().response();
    }

    public Response putUser(String messageBody, String userId) {
        return RestAssured.given().log().all().contentType(contentType).accept("application/json")
                .header("Authorization", authorizationBearer)
                .body(messageBody).put(baseUrl + "users/" + userId).then().log().all().extract().response();
    }
}
