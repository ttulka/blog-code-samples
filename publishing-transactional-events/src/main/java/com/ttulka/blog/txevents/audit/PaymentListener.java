package com.ttulka.blog.txevents.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class PaymentListener {

    @RabbitListener(queues = "paymentQueue")
    public void listen(String msg) {
        log.info("Event received: {}", msg);
    }
}
