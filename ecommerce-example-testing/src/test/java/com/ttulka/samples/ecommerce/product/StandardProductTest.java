package com.ttulka.samples.ecommerce.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StandardProductTest {

    @Test
    void name_is_set() {
        Product product = new StandardProduct(
                1L, "Test product", 123.45);

        assertThat(product.name()).isEqualTo("Test product");
    }

    @Test
    void price_is_set() {
        Product product = new StandardProduct(
                1L, "Test product", 123.45);

        assertThat(product.price()).isEqualTo(123.45);
    }

    @Test
    void standard_product_is_deliverable() {
        Product product = new StandardProduct(
                1L, "Test product", 123.45);

        assertThat(product.deliverable()).isTrue();
    }
}
