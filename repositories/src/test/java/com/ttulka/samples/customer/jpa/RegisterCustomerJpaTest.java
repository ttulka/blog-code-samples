package com.ttulka.samples.customer.jpa;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.FindCustomer;
import com.ttulka.samples.customer.Name;
import com.ttulka.samples.customer.RegisterCustomer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = CustomerJpaConfig.class)
@EnableJpaRepositories("com.ttulka.samples.customer.jpa")
@EntityScan("com.ttulka.samples.customer.jpa")
class RegisterCustomerJpaTest {

    @Autowired
    private RegisterCustomer registerCustomer;
    @Autowired
    private FindCustomer findCustomer;

    @Test
    void customer_is_registered() {
        Customer registered = registerCustomer.register(
                new Name("Fist", "Last"),
                new Email("test@example.com"));

        assertThat(registered).isEqualTo(new Customer(
                new Name("Fist", "Last"),
                new Email("test@example.com")));
    }

    @Test
    void registered_customer_is_found() {
        Customer registered = registerCustomer.register(
                new Name("Fist", "Last"),
                new Email("test@example.com"));

        Customer found = findCustomer
                .byEmail(new Email("test@example.com"));

        assertThat(registered).isEqualTo(found);
    }
}
