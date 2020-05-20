package com.ttulka.myshop.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class ProductConfiguration {

    @Bean
    ProductService productService(ProductRepository productRepository) {
        return new ProductServiceImpl(productRepository);
    }

    @Bean
    @Profile({"default", "jdbc"})
    ProductRepository productRepository(JdbcTemplate jdbcTemplate) {
        return new ProductRepositoryJdbc(jdbcTemplate);
    }
}
