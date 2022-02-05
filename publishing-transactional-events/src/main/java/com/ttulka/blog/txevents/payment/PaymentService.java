package com.ttulka.blog.txevents.payment;

import com.ttulka.blog.txevents.payment.event.PaymentApplied;
import com.ttulka.blog.txevents.payment.event.PaymentIssued;
import com.ttulka.blog.txevents.payment.event.PaymentValidated;
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
        issuePayment(payment);
        eventPublisher.publishEvent(new PaymentIssued(payment));

        validatePayment(payment);
        eventPublisher.publishEvent(new PaymentValidated(payment));

        applyPayment(payment);
        eventPublisher.publishEvent(new PaymentApplied(payment));
    }

    private void issuePayment(Payment payment) {
        log.info("issuing the payment: {}", payment);
        // ...
    }

    private void validatePayment(Payment payment) {
        log.info("validating the payment: {}", payment);
        // ...
    }

    private void applyPayment(Payment payment) {
        log.info("applying the payment: {}", payment);
        // ...
        throw new RuntimeException("Oops!");
    }
}
