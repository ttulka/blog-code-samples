package com.ttulka.samples.ecommerce.product;

import com.ttulka.samples.ecommerce.product.jpa.config.JpaProductConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@Import(JpaProductConfig.class)
class PutOnSaleJpaTest {

    @Autowired
    private PutOnSale putOnSale;

    @Test
    void product_is_put_on_sale() {
        var product = putOnSale.product(new PutOnSale.ToPutOnSale(
                "Test product", 123.45));

        assertAll(() -> assertThat(product.id()).isNotNull(),
                  () -> assertThat(product.name()).isEqualTo("Test product"),
                  () -> assertThat(product.price()).isEqualTo(123.45),
                  () -> assertThat(product.deliverable()).isTrue());
    }
}
