package com.ttulka.blog.samples.bad.product.jdbc;

import com.ttulka.blog.samples.bad.product.*;
import lombok.EqualsAndHashCode;
import org.springframework.jdbc.core.JdbcTemplate;

@EqualsAndHashCode(of = "id")
class ProductJdbc implements Product {

    private final ProductId id;
    private Title title;
    private Money price;
    private Availability availability;

    private final JdbcTemplate jdbc;

    public ProductJdbc(
            ProductId id,
            Title title, Money price, Availability availability,
            JdbcTemplate jdbcTemplate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.availability = availability;
        this.jdbc = jdbcTemplate;
    }

    @Override
    public ProductId id() {
        return id;
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
        jdbc.update("UPDATE products SET title = ? WHERE id = ?",
                newTitle.value(), id.value());
        title = newTitle;
    }

    @Override
    public void changePrice(Money newPrice) {
        jdbc.update("UPDATE products SET price = ? WHERE id = ?",
                newPrice.value(), id.value());
        price = newPrice;
    }
}
