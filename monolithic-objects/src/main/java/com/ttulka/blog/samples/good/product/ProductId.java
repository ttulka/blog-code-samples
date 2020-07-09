package com.ttulka.blog.samples.good.product;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class ProductId {

    private final long value;

    public ProductId(long value) {
        if (value < 1L) {
            throw new IllegalArgumentException("Cannot be less than one");
        }
        this.value = value;
    }

    public Long value() {
        return value;
    }
}
