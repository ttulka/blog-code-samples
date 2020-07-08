package com.ttulka.blog.samples.bad.product;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class ProductId {

    private final Long value;

    public ProductId(Long value) {
        if (value < 1L) {
            throw new IllegalArgumentException("Cannot be less than one");
        }
        this.value = value;
    }

    public Long value() {
        return value;
    }
}
