package com.ttulka.samples.product.jdbc;

import com.ttulka.samples.product.FindProducts;
import com.ttulka.samples.product.Money;
import com.ttulka.samples.product.Products;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class FindProductsJdbc implements FindProducts {

    private final SortByJdbc sortByDefault = new SortByJdbc(Products.SortBy.TITLE);

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Products cheaperThan(@NonNull Money price) {
        return new ProductsCheaperThan(price, sortByDefault, jdbcTemplate);
    }
}
