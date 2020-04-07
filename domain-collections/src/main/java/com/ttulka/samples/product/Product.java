package com.ttulka.samples.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "code")
@ToString
public final class Product {

    private final @NonNull Code code;
    private final @NonNull Title title;
    private final @NonNull Money price;

    public Code code() {
        return code;
    }

    public Title title() {
        return title;
    }

    public Money price() {
        return price;
    }
}
