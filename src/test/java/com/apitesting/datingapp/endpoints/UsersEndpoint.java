package com.apitesting.datingapp.endpoints;

import com.apitesting.datingapp.models.UsersListResponse;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UsersEndpoint {

    public static UsersListResponse getUsersByGender(String gender) {
        Response response = given()
                .param("gender", gender)
                .when()
                .get("/users");

        return response.as(UsersListResponse.class);
    }

    public static Response getUsersByGenderRaw(String gender) {
        return given()
                .param("gender", gender)
                .when()
                .get("/users");
    }

    public static Response getUsersWithPagination(String gender, Integer limit, Integer offset) {
        return given()
                .param("gender", gender)
                .param("limit", limit)
                .param("offset", offset)
                .when()
                .get("/users");
    }
}