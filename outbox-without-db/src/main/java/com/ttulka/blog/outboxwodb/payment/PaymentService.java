package com.ttulka.blog.outboxwodb.payment;

import com.ttulka.blog.outboxwodb.payment.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void collectPayment(Payment payment) {
        createPayment(payment);
        eventPublisher.publishEvent(new PaymentEvent(payment));
    }

    private void createPayment(Payment payment) {
        log.info("Creating the payment: {}", payment);
        // ...
        if ("X".equals(payment.getReference())) {
            throw new RuntimeException("Oops!");
        }
    }
}
