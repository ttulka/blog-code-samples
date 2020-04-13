package com.ttulka.samples.customer.jpa;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.FindCustomer;
import com.ttulka.samples.customer.Name;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class FindCustomerJpa implements FindCustomer {

    private final @NonNull CustomerRepository customerRepository;

    @Override
    public Customer byEmail(Email email) {
        return customerRepository.findByEmail(email.value())
                .map(this::toCustomer)
                .orElseGet(this::customerNotFound);
    }

    private Customer customerNotFound() {
        return new Customer(
                new Name("Unknown", "Unknown"),
                new Email("noreply@ttulka.com"));
    }

    private Customer toCustomer(CustomerRepository.CustomerEntry entry) {
        return new Customer(
                new Name(entry.firstName, entry.lastName),
                new Email(entry.email));
    }
}
