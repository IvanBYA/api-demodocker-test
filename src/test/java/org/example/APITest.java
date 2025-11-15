package org.example;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITest {


    @Test
    public void testGetUser() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
            .when()
            .get("/users/1")
            .then()
            .statusCode(200)
//            .body("id", equalTo(1))
//            .body("username", notNullValue())
            .extract()
            .response();

        System.out.println(response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());



        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("id"));

    }

    @Test
    public void testCreatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String requestBody = "{\n" +
                "  \"title\": \"My New Post\",\n" +
                "  \"body\": \"This is the content\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("My New Post"))
                .body("userId", equalTo(1))
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getBody().asString().contains("id"));


        System.out.println(response.getBody().asString());

    }

    @Test
    public void testDeletePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    public void testUpdatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String requestBody = "{\n" +
                "  \"id\": 1000000,\n" +
                "  \"title\": \"Updated Title\",\n" +
                "  \"body\": \"Updated body content\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("id", equalTo(1000000));
    }
}
