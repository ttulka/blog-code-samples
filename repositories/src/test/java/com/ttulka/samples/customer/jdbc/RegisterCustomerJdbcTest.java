package com.ttulka.samples.customer.jdbc;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.FindCustomer;
import com.ttulka.samples.customer.Name;
import com.ttulka.samples.customer.RegisterCustomer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = CustomerJdbcConfig.class)
class RegisterCustomerJdbcTest {

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
