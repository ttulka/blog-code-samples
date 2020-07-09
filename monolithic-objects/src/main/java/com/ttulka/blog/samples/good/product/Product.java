package com.ttulka.blog.samples.good.product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Product {

    private final @NonNull ProductId id;
    private final @NonNull Title title;
    private final @NonNull Money price;
    private final @NonNull Availability availability;

    public ProductId id() {
        return id;
    }

    public Title title() {
        return title;
    }

    public Money price() {
        return price;
    }

    public Availability availability() {
        return availability;
    }
}
