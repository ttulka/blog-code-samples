package com.ttulka.blog.txevents.payment;

import com.ttulka.blog.txevents.payment.event.PaymentApplied;
import com.ttulka.blog.txevents.payment.event.PaymentIssued;
import com.ttulka.blog.txevents.payment.event.PaymentValidated;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void collectPayment(Payment payment) {
        issuePayment(payment);
        rabbitTemplate.convertAndSend("paymentQueue",
                new PaymentIssued(payment).toString());

        validatePayment(payment);
        rabbitTemplate.convertAndSend("paymentQueue",
                new PaymentValidated(payment).toString());

        applyPayment(payment);
        rabbitTemplate.convertAndSend("paymentQueue",
                new PaymentApplied(payment).toString());
    }

    private void issuePayment(Payment payment) {
        // issuing the payment...

    }

    private void validatePayment(Payment payment) {
        // validating the payment...
    }

    private void applyPayment(Payment payment) {
        // applying the payment
        throw new RuntimeException("Oops!");
    }
}
