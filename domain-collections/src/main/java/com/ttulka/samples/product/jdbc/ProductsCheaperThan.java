package com.ttulka.samples.product.jdbc;

import java.util.Map;
import java.util.stream.Stream;

import com.ttulka.samples.product.Code;
import com.ttulka.samples.product.Money;
import com.ttulka.samples.product.Product;
import com.ttulka.samples.product.Products;
import com.ttulka.samples.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class ProductsCheaperThan implements Products {

    private final @NonNull Money cheaperThan;
    private final @NonNull SortByJdbc sortBy;

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Products sorted(SortBy by) {
        return new ProductsCheaperThan(cheaperThan, new SortByJdbc(by), jdbcTemplate);
    }

    @Override
    public Stream<Product> asStream() {
        return jdbcTemplate.queryForList(String.format(
                "SELECT code, title, price FROM products " +
                "WHERE price < ? ORDER BY %s", sortBy.jdbcValue()), cheaperThan.amount())
                .stream()
                .map(this::toProduct);
    }

    private Product toProduct(Map<String, Object> entry) {
        return new Product(
                new Code((String) entry.get("code")),
                new Title((String) entry.get("title")),
                new Money((Double) entry.get("price")));
    }
}
