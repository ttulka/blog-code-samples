package com.ttulka.blog.samples.good.product.jdbc;

import com.ttulka.blog.samples.good.product.Availability;
import com.ttulka.blog.samples.good.product.FindProduct;
import com.ttulka.blog.samples.good.product.Money;
import com.ttulka.blog.samples.good.product.Product;
import com.ttulka.blog.samples.good.product.ProductId;
import com.ttulka.blog.samples.good.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class FindProductJdbc implements FindProduct {

    private final JdbcTemplate jdbc;

    @Override
    public Product byId(ProductId id) {
        return jdbc.queryForList(
                "SELECT id, title, price, availability FROM products WHERE id = ?",
                id.value())
                .stream()
                .map(r -> new Product(
                        id,
                        new Title((String)r.get("title")),
                        new Money((Double)r.get("price")),
                        Availability.valueOf((String)r.get("availability"))))
                .findFirst()
                .orElseGet(() -> productNotFound(id));
    }

    private Product productNotFound(ProductId id) {
        return new Product(id, new Title("not found"), new Money(0.), Availability.UNKNOWN);
    }
}
