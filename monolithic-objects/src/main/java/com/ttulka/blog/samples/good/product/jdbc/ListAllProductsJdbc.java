package com.ttulka.blog.samples.good.product.jdbc;

import java.util.Collection;
import java.util.stream.Collectors;

import com.ttulka.blog.samples.good.product.Availability;
import com.ttulka.blog.samples.good.product.ListAllProducts;
import com.ttulka.blog.samples.good.product.Money;
import com.ttulka.blog.samples.good.product.Product;
import com.ttulka.blog.samples.good.product.ProductId;
import com.ttulka.blog.samples.good.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ListAllProductsJdbc implements ListAllProducts {

    private final JdbcTemplate jdbc;

    @Override
    public Collection<Product> list() {
        return jdbc.queryForList("SELECT id, title, price, availability FROM products")
                .stream()
                .map(r -> new Product(
                        new ProductId((Long)r.get("id")),
                        new Title((String)r.get("title")),
                        new Money((Double)r.get("price")),
                        Availability.valueOf((String)r.get("availability"))))
                .collect(Collectors.toList());
    }
}
