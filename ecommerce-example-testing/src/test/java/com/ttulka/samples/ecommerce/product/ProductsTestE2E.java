package com.ttulka.samples.ecommerce.product;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Testcontainers
class ProductsTestE2E {

    @Container
    public GenericContainer application = new GenericContainer(
            new ImageFromDockerfile()
                    .withFileFromPath("app.jar", Path.of("build/libs/application.jar"))
                    .withDockerfileFromBuilder(builder -> builder
                            .from("openjdk:11-jre-slim")
                            .copy("app.jar", "/app.jar")
                            .entryPoint("java", "-jar", "/app.jar")
                            .build()))
            .withExposedPorts(8080);

    private int port;

    @BeforeEach
    void setUp() {
        port = application.getFirstMappedPort();
    }

    @Test
    void product_is_promoted() {
        with()
                .port(port)
                .basePath("/products/promoted")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Test promo product\",\"price\":123.45}")
                .post()
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", equalTo("Test promo product"))
                .body("price", equalTo(123.45f));
    }

    @Test
    void product_is_put_on_sale() {
        with()
                .port(port)
                .basePath("/products/standard")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Test standard product\",\"price\":123.45}")
                .post()
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", equalTo("Test standard product"))
                .body("price", equalTo(123.45f));
    }

    @Test
    void all_promoted_products_are_returned() {
        var productId = with()
                .port(port)
                .basePath("/products/promoted")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Test promo product\",\"price\":123.45}")
                .post()
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        with()
                .port(port)
                .basePath("/products/promoted")
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(productId))
                .body("[0].name", equalTo("Test promo product"))
                .body("[0].price", equalTo(123.45f));
    }

    @Test
    void all_standard_products_are_returned() {
        var productId = with()
                .port(port)
                .basePath("/products/standard")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Test standard product\",\"price\":123.45}")
                .post()
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        with()
                .port(port)
                .basePath("/products/standard")
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(productId))
                .body("[0].name", equalTo("Test standard product"))
                .body("[0].price", equalTo(123.45f));
    }
}
