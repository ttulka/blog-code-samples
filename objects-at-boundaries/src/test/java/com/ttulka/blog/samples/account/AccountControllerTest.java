package com.ttulka.blog.samples.account;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AccountControllerTest.TestConfig.class)
@EnableAutoConfiguration
class AccountControllerTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DirtiesContext
    void user_is_logged_in() {
        // register
        with()
            .port(port)
            .basePath("/account").
        when()
            .contentType(ContentType.JSON)
            .body("{\"username\":\"test\",\"password\":\"pwd1\",\"email\":\"test@example.com\"}")
            .post().
        then()
            .statusCode(201);

        // log in
        with()
            .port(port)
            .basePath("/account").
        when()
            .param("username", "test")
            .param("password", "pwd1")
            .get().
        then()
            .statusCode(200)
            .body("username", is("test"));
    }

    @Test
    @DirtiesContext
    void account_is_registered() {
        with()
            .port(port)
            .basePath("/account").
        when()
            .contentType(ContentType.JSON)
            .body("{\"Ausername\":\"test\",\"Apassword\":\"pwd1\",\"Aemail\":\"test@example.com\"}")
            .post().
        then()
            .statusCode(201);
    }

    @Configuration
    @Import({AccountController.class, UserAccounts.class})
    static class TestConfig {
    }
}
