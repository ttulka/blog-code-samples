package com.ttulka.samples.customer.jpa;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.FindCustomer;
import com.ttulka.samples.customer.Name;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = CustomerJpaConfig.class)
@EnableJpaRepositories("com.ttulka.samples.customer.jpa")
@EntityScan("com.ttulka.samples.customer.jpa")
@Sql(statements = "INSERT INTO customers_jpa (id, first_name, last_name, email) VALUES(" +
                  "STRINGTOUTF8('f5ed1227-01e1-4bd1-b928-4fa9dd372d7a')," +
                  "'Fist', 'Last', 'test@example.com')")
class FindCustomerJpaTest {

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
