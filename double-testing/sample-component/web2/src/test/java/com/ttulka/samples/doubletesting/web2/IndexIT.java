package com.ttulka.samples.doubletesting.web2;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestPropertySource(properties = "doubletesting.path.web2=web2")
class IndexIT extends IndexTestBase {

    @Configuration
    static class ITConfig {
        // intentionally empty
    }
}