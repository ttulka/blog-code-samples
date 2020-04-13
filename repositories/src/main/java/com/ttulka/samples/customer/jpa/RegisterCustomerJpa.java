package com.ttulka.samples.customer.jpa;

import com.ttulka.samples.customer.Customer;
import com.ttulka.samples.customer.Email;
import com.ttulka.samples.customer.Name;
import com.ttulka.samples.customer.RegisterCustomer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class RegisterCustomerJpa implements RegisterCustomer {

    private final @NonNull CustomerRepository customerRepository;

    @Override
    public Customer register(Name name, Email email) {
        var entry = customerRepository.save(new CustomerRepository.CustomerEntry(
                null, name.fist(), name.last(), email.value()
        ));
        return new Customer(
                new Name(entry.firstName, entry.lastName),
                new Email(entry.email));
    }
}
