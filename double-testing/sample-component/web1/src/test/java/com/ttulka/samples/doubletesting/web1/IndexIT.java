package com.ttulka.samples.doubletesting.web1;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestPropertySource(properties = "doubletesting.path.web1=web1")
class IndexIT extends IndexTestBase {

    @Configuration
    static class ITConfig {
        // intentionally empty
    }
}