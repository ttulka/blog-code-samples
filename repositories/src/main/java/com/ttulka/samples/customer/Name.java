package com.ttulka.samples.customer;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class Name {

    private final @NonNull String first;
    private final @NonNull String last;

    public String fist() {
        return first;
    }

    public String last() {
        return last;
    }

    public String full() {
        return first + " " + last;
    }
}
