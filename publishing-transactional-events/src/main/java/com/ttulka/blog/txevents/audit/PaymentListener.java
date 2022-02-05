package com.ttulka.blog.txevents.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class PaymentListener {

    @RabbitListener(queues = "paymentQueue")
    public void listen(String msg) {
        log.info("Event received: {}", msg);
    }
}
