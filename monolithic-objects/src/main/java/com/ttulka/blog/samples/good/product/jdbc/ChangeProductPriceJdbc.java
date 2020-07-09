package com.ttulka.blog.samples.good.product.jdbc;

import com.ttulka.blog.samples.good.product.ChangeProductPrice;
import com.ttulka.blog.samples.good.product.Money;
import com.ttulka.blog.samples.good.product.ProductId;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChangeProductPriceJdbc implements ChangeProductPrice {

    private final JdbcTemplate jdbc;

    @Override
    public void change(ProductId id, Money newPrice) {
        jdbc.update("UPDATE products SET price = ? WHERE id = ?",
                    newPrice.value(), id.value());
    }
}
