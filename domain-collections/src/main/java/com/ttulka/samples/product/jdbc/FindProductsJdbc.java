package com.ttulka.samples.product.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.product.Code;
import com.ttulka.samples.product.FindProducts;
import com.ttulka.samples.product.Money;
import com.ttulka.samples.product.Product;
import com.ttulka.samples.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class FindProductsJdbc implements FindProducts {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> cheaperThan(Money money) {
        return jdbcTemplate.queryForList(
                "SELECT code, title, price FROM products WHERE price < ?", money.amount())
                .stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Product toProduct(Map<String, Object> entry) {
        return new Product(
                new Code((String) entry.get("code")),
                new Title((String) entry.get("title")),
                new Money((Double) entry.get("price")));
    }
}
