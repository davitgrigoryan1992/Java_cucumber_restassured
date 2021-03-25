package gorest.co.in.integration.helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiForUsers {

    public Response getUsersListResponse = null;
    private String basePath = "https://gorest.co.in/public-api/";

    public Response createUser() {
        return RestAssured.given().contentType("application/json").accept("application/json")
                .header("Authorization", "Bearer ACCESS-TOKEN")
                .body(basePath + "users").post().then().log().all().extract().response();
    }

    public Response getUsersList() {
        return RestAssured.given().contentType("application/json").accept("application/json")
                .header("Authorization", "Bearer ACCESS-TOKEN")
                .get(basePath + "users").then().log().all().extract().response();
    }
}
