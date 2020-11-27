package com.ttulka.samples.ecommerce.product;

import com.ttulka.samples.ecommerce.product.jpa.config.JpaProductConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaProductConfig.class)
class FindProductsJpaTest {

    @Autowired
    private FindProducts findProducts;
    @Autowired
    private Promote promote;
    @Autowired
    private PutOnSale putOnSale;

    @Test
    void all_standard_products_are_found() {
        var product1 = putOnSale.product(new PutOnSale.ToPutOnSale(
                "Test 1", 1.5));
        var product2 = putOnSale.product(new PutOnSale.ToPutOnSale(
                "Test 2", 2.5));

        var products = findProducts.standards();

        assertThat(products).contains(product1, product2);
    }

    @Test
    void all_promo_products_are_found() {
        var product1 = promote.product(new Promote.ToPromote(
                "Test 1", 1.5));
        var product2 = promote.product(new Promote.ToPromote(
                "Test 2", 2.5));

        var products = findProducts.promos();

        assertThat(products).contains(product1, product2);
    }
}
