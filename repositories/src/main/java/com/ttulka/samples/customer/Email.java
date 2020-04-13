package com.ttulka.samples.customer;

import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Email {

    private final @NonNull String email;

    public Email(@NonNull String email) {
        if (!isValid(email)) {
            throw new IllegalArgumentException("No valid email! " + email);
        }
        this.email = email;
    }

    public String value() {
        return email;
    }

    private boolean isValid(String email) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .matcher(email).matches();
    }
}
