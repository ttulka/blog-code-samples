package com.ttulka.blog.samples.good.product.jdbc;

import com.ttulka.blog.samples.good.product.ChangeProductTitle;
import com.ttulka.blog.samples.good.product.ProductId;
import com.ttulka.blog.samples.good.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChangeProductTitleJdbc implements ChangeProductTitle {

    private final JdbcTemplate jdbc;

    @Override
    public void change(ProductId id, Title newTitle) {
        jdbc.update("UPDATE products SET title = ? WHERE id = ?",
                    newTitle.value(), id.value());
    }
}
