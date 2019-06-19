package com.ttulka.samples.doubletesting.web1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(SpringExtension.class)
abstract class IndexTestBase {

    @Value("http://localhost:${local.server.port:8080}/${doubletesting.path.web1:}")
    private String url;

    @Test
    void indexResource_shouldContainWeb1() {
        given()
                .baseUri(url)
                .basePath("/").
        when()
                .get().
        then()
                .statusCode(200)
                .contentType("text/plain")
                .body(containsString("Web1"));
    }
}
