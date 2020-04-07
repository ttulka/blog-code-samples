package com.ttulka.samples.product.jdbc;

import com.ttulka.samples.product.Products;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
final class SortByJdbc {

    private final @NonNull Products.SortBy sortBy;

    public String jdbcValue() {
        switch (sortBy) {
            case PRICE: return "price";
            default: return "title";
        }
    }
}
