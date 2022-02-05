package com.ttulka.blog.txevents.payment.event;

import com.ttulka.blog.txevents.payment.Payment;
import lombok.ToString;

@ToString(callSuper = true)
public class PaymentValidated extends PaymentEvent {

    public PaymentValidated(Payment payment) {
        super(payment);
    }
}
