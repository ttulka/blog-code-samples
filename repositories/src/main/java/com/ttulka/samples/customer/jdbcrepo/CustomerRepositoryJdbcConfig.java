package com.ttulka.samples.customer.jdbcrepo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class CustomerRepositoryJdbcConfig {

    @Bean
    CustomerRepositoryJdbc customerRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        return new CustomerRepositoryJdbc(jdbcTemplate);
    }
}
