package com.ttulka.blog.samples.solid;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VolunteerTest {

    @Test
    void full_name_is_provided() {
        Volunteer volunteer = new Volunteer(
                UUID.randomUUID().toString(), "John", "Smith");

        assertThat(volunteer.fullName()).isEqualTo("John Smith");
    }

    @Test
    void is_registered() {
        RegistrableEmployee volunteer = new RegistrableEmployee(
                new Volunteer(UUID.randomUUID().toString(), "John", "Smith"),
                new EmployeeRegistryInMem()
        );
        volunteer.register();

        assertThat(volunteer.isRegistered()).isTrue();
    }

    @Test
    void is_not_registered() {
        RegistrableEmployee volunteer = new RegistrableEmployee(
                new Volunteer(UUID.randomUUID().toString(), "John", "Smith"),
                new EmployeeRegistryInMem()
        );

        assertThat(volunteer.isRegistered()).isFalse();
    }
}
