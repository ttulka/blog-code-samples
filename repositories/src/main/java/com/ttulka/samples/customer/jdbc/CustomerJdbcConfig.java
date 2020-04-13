package com.ttulka.samples.customer.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class CustomerJdbcConfig {

    @Bean
    FindCustomerJdbc findCustomerJdbc(JdbcTemplate jdbcTemplate) {
        return new FindCustomerJdbc(jdbcTemplate);
    }

    @Bean
    RegisterCustomerJdbc registerCustomerJdbc(JdbcTemplate jdbcTemplate) {
        return new RegisterCustomerJdbc(jdbcTemplate);
    }
}
