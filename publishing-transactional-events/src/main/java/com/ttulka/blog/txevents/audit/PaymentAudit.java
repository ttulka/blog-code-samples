package com.ttulka.blog.txevents.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class PaymentAudit {

    @RabbitListener(queues = "paymentQueue")
    public void listen(String event) {
        log.info("Event received: {}", event);
    }
}
