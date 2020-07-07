package com.ttulka.blog.samples.monolithic.product.jdbc;

import com.ttulka.blog.samples.monolithic.product.Availability;
import com.ttulka.blog.samples.monolithic.product.Money;
import com.ttulka.blog.samples.monolithic.product.Product;
import com.ttulka.blog.samples.monolithic.product.Title;
import org.springframework.jdbc.core.JdbcTemplate;

class ProductJdbc implements Product {

    private final Long id;
    private Title title;
    private Money price;
    private Availability availability;

    private final JdbcTemplate jdbcTemplate;

    public ProductJdbc(
            Long id,
            Title title, Money price, Availability availability,
            JdbcTemplate jdbcTemplate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.availability = availability;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Title title() {
        return title;
    }

    @Override
    public Money price() {
        return price;
    }

    @Override
    public Availability availability() {
        return availability;
    }

    @Override
    public void changeTitle(Title newTitle) {
        jdbcTemplate.update("UPDATE products SET title = ? WHERE id = ?",
                newTitle.value(), id);
        title = newTitle;
    }

    @Override
    public void changePrice(Money newPrice) {
        jdbcTemplate.update("UPDATE products SET price = ? WHERE id = ?",
                newPrice.value(), id);
        price = newPrice;
    }
}
