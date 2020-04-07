package com.ttulka.samples.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Money {

    private final @NonNull Double amount;

    public Money(@NonNull Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Money amount cannot be less than zero: " + amount);
        }
        this.amount = amount;
    }

    public Double amount() {
        return amount;
    }
}
