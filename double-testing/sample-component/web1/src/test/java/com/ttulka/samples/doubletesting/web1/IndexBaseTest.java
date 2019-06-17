package com.ttulka.samples.doubletesting.web1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class IndexBaseTest {

    @Value("http://localhost:${local.server.port:8080}/${doubletesting.path.web1:}")
    private String url;

    private WebDriver webDriver;

    @BeforeEach
    void setupDriver() {
        webDriver = new HtmlUnitDriver(false);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + url);
        webDriver.get(url);
    }

    @AfterEach
    void closeDriver() {
        webDriver.close();
    }

    @Test
    void indexPageTitle_shouldContainWeb1() {
        assertEquals("Web1", webDriver.getTitle());
    }

    @Test
    void indexPageHeading_shouldContainWeb1() {
        assertEquals("Web1", webDriver.findElement(By.tagName("h1")).getText());
    }
}
