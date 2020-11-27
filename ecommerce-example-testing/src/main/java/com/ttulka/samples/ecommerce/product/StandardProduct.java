package com.ttulka.samples.ecommerce.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Standard product to sell and deliver.
 */
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
@ToString
public class StandardProduct implements Product {

    private final @NonNull Long id;
    private final @NonNull String name;
    private final @NonNull Double price;

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Double price() {
        return price;
    }

    @Override
    public boolean deliverable() {
        return true;
    }
}
