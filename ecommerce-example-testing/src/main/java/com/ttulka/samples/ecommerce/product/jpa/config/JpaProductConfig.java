package com.ttulka.samples.ecommerce.product.jpa.config;

import com.ttulka.samples.ecommerce.product.jpa.FindProductsJpa;
import com.ttulka.samples.ecommerce.product.jpa.PromoteJpa;
import com.ttulka.samples.ecommerce.product.jpa.PutOnSaleJpa;
import com.ttulka.samples.ecommerce.product.jpa.repository.ProductRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaProductConfig {

    @Bean
    PromoteJpa saveProductJpa(ProductRepository repository) {
        return new PromoteJpa(repository);
    }

    @Bean
    PutOnSaleJpa putOnSaleJpa(ProductRepository repository) {
        return new PutOnSaleJpa(repository);
    }

    @Bean
    FindProductsJpa findProductsJpa(ProductRepository repository) {
        return new FindProductsJpa(repository);
    }
}
