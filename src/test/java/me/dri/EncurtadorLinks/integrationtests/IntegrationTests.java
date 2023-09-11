package me.dri.EncurtadorLinks.integrationtests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
public class IntegrationTests {

    @Container
    public PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test-container")
            .withUsername("sa")
            .withPassword("sa");


    @BeforeAll
    public static void setuo() {
        RestAssured.baseURI = "http://54.224.59.14 ";
    }

    @Test
    public void testCRUDfindAll() {
        given()
                .contentType(ContentType.JSON)
                .get("/encurtador/all")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCRUDcreateShortenerUrl() {
        String request = "{\"urlBase\": \"https://www.youtube.com/watch?v=TkRkyHHYDtU\"}";
       String createdObject =  given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/encurtador/encurtar")
                .then()
                .statusCode(201)
                .extract()
                .asString();

        assertThat(createdObject, containsString("urlShortener"));

    }


}



