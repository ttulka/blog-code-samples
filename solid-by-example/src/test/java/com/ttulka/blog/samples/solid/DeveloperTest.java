package com.ttulka.blog.samples.solid;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeveloperTest {

    @Test
    void full_name_is_provided() {
        Developer developer = new Developer(UUID.randomUUID().toString(), "John", "Smith");

        assertThat(developer.fullName()).isEqualTo("John Smith");
    }

    @Test
    void employee_is_registered() {
        Developer developer = new Developer(UUID.randomUUID().toString(), "John", "Smith");
        developer.register();

        assertThat(developer.isRegistered()).isTrue();
    }

    @Test
    void employee_is_not_registered() {
        Developer developer = new Developer(UUID.randomUUID().toString(), "John", "Smith");

        assertThat(developer.isRegistered()).isFalse();
    }
}
