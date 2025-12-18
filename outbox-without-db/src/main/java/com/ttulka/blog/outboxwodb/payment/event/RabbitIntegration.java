package com.ttulka.blog.outboxwodb.payment.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
class RabbitIntegration {

    private final RabbitTemplate rabbitTemplate;

    private static int ISSUE = 0;

    @Retryable(maxRetries = 5, delay = 1000, multiplier = 2)
    @TransactionalEventListener
    public void handleEvent(PaymentEvent event) {
        log.info("Sending event: {}", event);
        if ("Y".equals(event.getReference())) {
            if (++ISSUE < 3) {
                log.error("Boom!");
                throw new RuntimeException("Boom!");
            }
            else ISSUE = 0;
        }
        rabbitTemplate.convertAndSend(
                "paymentQueue", event.toString());
    }
}
