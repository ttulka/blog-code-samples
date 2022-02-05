package com.ttulka.blog.txevents.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
class RabbitIntegration {

    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener
    public void handleEvent(PaymentEvent event) {
        rabbitTemplate.convertAndSend(
                "paymentQueue", event.toString());
    }
}
