package com.ttulka.samples.doubletesting.web2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
abstract class IndexTestBase {

    @Value("http://localhost:${local.server.port:8080}/${doubletesting.path.web2:}")
    private String url;

    private WebDriver webDriver;

    @BeforeEach
    void setupDriver() {
        webDriver = new HtmlUnitDriver(false);
        webDriver.get(url);
    }

    @AfterEach
    void closeDriver() {
        webDriver.close();
    }

    @Test
    void indexPageTitle_shouldContainWeb2() {
        assertEquals("Web2", webDriver.getTitle());
    }

    @Test
    void indexPageHeading_shouldContainWeb2() {
        assertEquals("Web2", webDriver.findElement(By.tagName("h1")).getText());
    }
}
