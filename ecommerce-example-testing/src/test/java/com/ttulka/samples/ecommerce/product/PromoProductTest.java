package com.ttulka.samples.ecommerce.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PromoProductTest {

    @Test
    void name_is_set() {
        Product product = new PromoProduct(
                1L, "Test product", 123.45);

        assertThat(product.name()).isEqualTo("Test product");
    }

    @Test
    void price_is_set() {
        Product product = new PromoProduct(
                1L, "Test product", 123.45);

        assertThat(product.price()).isEqualTo(123.45);
    }

    @Test
    void promo_product_is_not_deliverable() {
        Product product = new PromoProduct(
                1L, "Test product", 123.45);

        assertThat(product.deliverable()).isFalse();
    }
}
