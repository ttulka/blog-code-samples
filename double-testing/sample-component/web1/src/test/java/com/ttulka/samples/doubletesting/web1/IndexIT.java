package com.ttulka.samples.doubletesting.web1;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "doubletesting.path.web1=web1")
@Tag("IntegrationTest")
public class IndexIT extends IndexBaseTest {
}
