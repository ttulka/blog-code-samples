package com.ttulka.blog.txevents;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TxEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxEventsApplication.class, args);
	}

    // create a new queue
	@Bean
	Queue paymentQueue() {
		return new Queue("paymentQueue", false);
	}
}
