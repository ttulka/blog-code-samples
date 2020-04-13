package com.ttulka.samples.customer.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CustomerJpaConfig {

    @Bean
    FindCustomerJpa findCustomerJpa(CustomerRepository customerRepository) {
        return new FindCustomerJpa(customerRepository);
    }

    @Bean
    RegisterCustomerJpa registerCustomerJpa(CustomerRepository customerRepository) {
        return new RegisterCustomerJpa(customerRepository);
    }
}
