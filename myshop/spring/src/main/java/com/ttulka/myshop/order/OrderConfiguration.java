package com.ttulka.myshop.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class OrderConfiguration {

    @Bean
    OrderService orderService(OrderRepository orderRepository) {
        return new OrderServiceImpl(orderRepository);
    }

    @Bean
    @Profile({"default", "jdbc"})
    OrderRepository orderRepository(JdbcTemplate jdbcTemplate) {
        return new OrderRepositoryJdbc(jdbcTemplate);
    }
}
