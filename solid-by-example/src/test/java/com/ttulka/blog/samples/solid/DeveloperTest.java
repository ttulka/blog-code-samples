package com.ttulka.blog.samples.solid;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DeveloperTest {

    @Test
    void full_name_is_provided() {
        Developer developer = new Developer(
                UUID.randomUUID().toString(), "John", "Smith");

        assertThat(developer.fullName()).isEqualTo("John Smith");
    }

    @Test
    void is_registered() {
        RegistrableEmployee developer = new RegistrableEmployee(
                new Developer(UUID.randomUUID().toString(), "John", "Smith"),
                new EmployeeRegistryInMem()
        );
        developer.register();

        assertThat(developer.isRegistered()).isTrue();
    }

    @Test
    void is_not_registered() {
        RegistrableEmployee developer = new RegistrableEmployee(
                new Developer(UUID.randomUUID().toString(), "John", "Smith"),
                new EmployeeRegistryInMem()
        );

        assertThat(developer.isRegistered()).isFalse();
    }
    
    @Test
    void salary_is_1000() {
        Developer developer = new Developer(
                UUID.randomUUID().toString(), "John", "Smith");

        assertThat(developer.salary()).isCloseTo(1000.00, within(0.0));
    }
}
