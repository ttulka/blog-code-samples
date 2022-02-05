package com.ttulka.blog.txevents.payment;

import com.ttulka.blog.txevents.payment.event.PaymentApplied;
import com.ttulka.blog.txevents.payment.event.PaymentIssued;
import com.ttulka.blog.txevents.payment.event.PaymentValidated;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
class RabbitIntegration {

    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener
    public void handleEvent(PaymentIssued event) {
        rabbitTemplate.convertAndSend(
                "paymentQueue", event.toString());
    }

    @TransactionalEventListener
    public void handleEvent(PaymentValidated event) {
        rabbitTemplate.convertAndSend(
                "paymentQueue", event.toString());
    }

    @TransactionalEventListener
    public void handleEvent(PaymentApplied event) {
        rabbitTemplate.convertAndSend(
                "paymentQueue", event.toString());
    }
}
