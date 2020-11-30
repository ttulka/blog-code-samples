package com.ttulka.samples.ecommerce.product;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
class ProductsTestUI {

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

    private WebDriver webDriver;

    @BeforeEach
    void setUp() {
        port = application.getFirstMappedPort();

        webDriver = new HtmlUnitDriver();
        webDriver.get(String.format("http://localhost:%d/ui/products", port));
    }
    
    @AfterEach
    void tearDown() {
        webDriver.close();
    }

    @Test
    void product_is_promoted() {
        var form = webDriver.findElement(By.id("form-promote"));
        form.findElement(By.className("name")).sendKeys("Test");
        form.findElement(By.className("price")).sendKeys("123.45");
        form.findElement(By.className("submit")).click();

        var list = webDriver.findElement(By.id("products-list__promo"))
                .findElements(By.className("product"));

        assertAll(
                () -> assertThat(list).hasSize(1),
                () -> assertThat(list.get(0).findElement(By.className("id")).getText()).isNotNull(),
                () -> assertThat(list.get(0).findElement(By.className("name")).getText()).isEqualTo("Test"),
                () -> assertThat(list.get(0).findElement(By.className("price")).getText()).isEqualTo("123.45")
        );
    }

    @Test
    void product_is_put_on_sale() {
        var form = webDriver.findElement(By.id("form-sale"));
        form.findElement(By.className("name")).sendKeys("Test");
        form.findElement(By.className("price")).sendKeys("123.45");
        form.findElement(By.className("submit")).click();

        var list = webDriver.findElement(By.id("products-list__standard"))
                .findElements(By.className("product"));

        assertAll(
                () -> assertThat(list).hasSize(1),
                () -> assertThat(list.get(0).findElement(By.className("id")).getText()).isNotNull(),
                () -> assertThat(list.get(0).findElement(By.className("name")).getText()).isEqualTo("Test"),
                () -> assertThat(list.get(0).findElement(By.className("price")).getText()).isEqualTo("123.45")
        );
    }
}
