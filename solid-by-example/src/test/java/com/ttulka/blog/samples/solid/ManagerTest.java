package com.ttulka.blog.samples.solid;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ManagerTest {

    @Test
    void full_name_is_provided() {
        Manager manager = new Manager(
                UUID.randomUUID().toString(), "John", "Smith", new EmployeeRegistryInMem());

        assertThat(manager.fullName()).isEqualTo("John Smith");
    }

    @Test
    void employee_is_registered() {
        Manager manager = new Manager(
                UUID.randomUUID().toString(), "John", "Smith", new EmployeeRegistryInMem());
        manager.register();

        assertThat(manager.isRegistered()).isTrue();
    }

    @Test
    void employee_is_not_registered() {
        Manager manager = new Manager(
                UUID.randomUUID().toString(), "John", "Smith", new EmployeeRegistryInMem());

        assertThat(manager.isRegistered()).isFalse();
    }
}
