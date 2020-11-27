package com.ttulka.samples.ecommerce.product;

import java.util.HashMap;
import java.util.Map;

import com.ttulka.samples.ecommerce.Application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsTestIT {

    @LocalServerPort
    private int port;

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
    void all_promoted_products_are_returned(@Autowired Promote promote) {
        var product = promote.product(new Promote.ToPromote(
                "Promo Test", 123.45));

        var products = with()
                .port(port)
                .basePath("/products/promoted")
                .get()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(1))
                .extract()
                .jsonPath().getList("$", HashMap.class);

        assertThat(products).contains(new HashMap<>(Map.of(
                "id", product.id().intValue(),
                "name", product.name(),
                "price", product.price())));
    }

    @Test
    void all_standard_products_are_returned(@Autowired PutOnSale putOnSale) {
        var product = putOnSale.product(new PutOnSale.ToPutOnSale(
                "Standard Test", 123.45));

        var products = with()
                .port(port)
                .basePath("/products/standard")
                .get()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(1))
                .extract()
                .jsonPath().getList("$", HashMap.class);

        assertThat(products).contains(new HashMap<>(Map.of(
                "id", product.id().intValue(),
                "name", product.name(),
                "price", product.price())));
    }
}
