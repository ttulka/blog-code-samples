package com.ttulka.blog.samples.solid;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class ManagerTest {

    @Test
    void full_name_is_provided() {
        Manager manager = new Manager(
                UUID.randomUUID().toString(), "John", "Smith");

        assertThat(manager.fullName()).isEqualTo("John Smith");
    }

    @Test
    void is_registered() {
        RegistrableEmployee manager = new RegistrableEmployee(
                new Manager(UUID.randomUUID().toString(), "John", "Smith"),
                new EmployeeRegistryInMem()
        );
        manager.register();

        assertThat(manager.isRegistered()).isTrue();
    }

    @Test
    void is_not_registered() {
        RegistrableEmployee manager = new RegistrableEmployee(
                new Manager(UUID.randomUUID().toString(), "John", "Smith"),
                new EmployeeRegistryInMem()
        );

        assertThat(manager.isRegistered()).isFalse();
    }

    @Test
    void salary_is_2000() {
        Manager manager = new Manager(
                UUID.randomUUID().toString(), "John", "Smith");

        assertThat(manager.salary()).isCloseTo(2000.00, within(0.0));
    }
}
