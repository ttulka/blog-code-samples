package com.ttulka.samples.customer.jdbc;

import java.util.UUID;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.Name;
import com.ttulka.samples.customer.RegisterCustomer;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class RegisterCustomerJdbc implements RegisterCustomer {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Customer register(Name name, Email email) {
        jdbcTemplate.update(
                "INSERT INTO customers VALUES(?, ?, ?, ?)",
                UUID.randomUUID().toString(), name.fist(), name.last(), email.value());

        return new Customer(name, email);
    }
}
