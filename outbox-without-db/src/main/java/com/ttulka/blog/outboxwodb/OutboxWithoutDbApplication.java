package com.ttulka.blog.outboxwodb;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableResilientMethods
public class OutboxWithoutDbApplication {

	// create a new queue
	@Bean
	Queue paymentQueue() {
		return new Queue("paymentQueue", false);
	}

	static void main(String[] args) {
		SpringApplication.run(OutboxWithoutDbApplication.class, args);
	}
}
