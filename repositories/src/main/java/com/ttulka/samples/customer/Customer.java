package com.ttulka.samples.customer;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class Customer {

    private final Name name;
    private final Email email;

    public Name name() {
        return name;
    }

    public Email email() {
        return email;
    }
}
