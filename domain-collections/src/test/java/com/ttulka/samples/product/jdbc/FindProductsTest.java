package com.ttulka.samples.product.jdbc;

import com.ttulka.samples.product.Code;
import com.ttulka.samples.product.FindProducts;
import com.ttulka.samples.product.Money;
import com.ttulka.samples.product.Product;
import com.ttulka.samples.product.Title;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = FindProductsJdbc.class)
@Sql(statements = "INSERT INTO products VALUES " +
                  "  ('001', 'A', 3.00)," +
                  "  ('002', 'B', 2.50)," +
                  "  ('003', 'C', 1.00);")
class FindProductsTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void products_cheaper_than_an_amount_of_money_are_found() {
        var products = findProducts.cheaperThan(new Money(3.00));

        assertThat(products).containsExactly(
                new Product(new Code("002"), new Title("B"), new Money(2.50)),
                new Product(new Code("003"), new Title("C"), new Money(1.00)));
    }
}
