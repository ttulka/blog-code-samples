package com.ttulka.samples.customer.jdbc;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.FindCustomer;
import com.ttulka.samples.customer.Name;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = CustomerJdbcConfig.class)
@Sql(statements = "INSERT INTO customers VALUES('001', 'Fist', 'Last', 'test@example.com')")
class FindCustomerJdbcTest {

    @Autowired
    private FindCustomer findCustomer;

    @Test
    void customer_by_email_is_found() {
        Customer found = findCustomer.byEmail(new Email("test@example.com"));

        assertThat(found).isEqualTo(new Customer(
                new Name("Fist", "Last"),
                new Email("test@example.com")));
    }

    @Test
    void customer_by_email_is_not_found() {
        Customer found = findCustomer.byEmail(new Email("_WRONG_@example.com"));

        assertThat(found).isNotEqualTo(new Customer(
                new Name("Fist", "Last"),
                new Email("test@example.com")));
    }
}
