package com.ttulka.samples.customer.jdbc;

import java.util.Map;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.FindCustomer;
import com.ttulka.samples.customer.Name;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class FindCustomerJdbc implements FindCustomer {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Customer byEmail(Email email) {
        return jdbcTemplate.queryForList(
                "SELECT firstName, lastName, email FROM customers WHERE email = ?", email.value())
                .stream()
                .findAny()
                .map(this::toCustomer)
                .orElseGet(this::customerNotFound);
    }

    private Customer customerNotFound() {
        return new Customer(
                new Name("Unknown", "Unknown"),
                new Email("noreply@ttulka.com"));
    }

    private Customer toCustomer(Map<String, Object> entry) {
        return new Customer(
                new Name((String) entry.get("firstName"), (String) entry.get("lastName")),
                new Email((String) entry.get("email")));
    }
}
