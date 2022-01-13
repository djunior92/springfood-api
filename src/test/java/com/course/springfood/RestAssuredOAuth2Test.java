package com.course.springfood;

import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Base64;

import static io.restassured.RestAssured.given;


public class RestAssuredOAuth2Test {

    public static String clientId = "springfood-web";
    public static String clientPassword = "web123";
    public static String scope = "READ WRITE";
    public static String username = "joao@springfood.com";
    public static String password = "123";

    public static String encodeToBase64(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public Response doAuth() {
        String authorization = encodeToBase64(clientId, clientPassword);

        return
                given()
                    .basePath("/oauth/token")
                    .header("Authorization", "Basic " + authorization)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .formParam("username", username)
                    .formParam("password", password)
                    .formParam("grant_type", "password")
                    .post()
                .then()
                    .statusCode(200)
                    .extract()
                .response();
    }

    public String parseToken(Response response) {
        return response.jsonPath().getString("access_token");
    }

    public String getToken() {
        return parseToken(doAuth());
    }

}