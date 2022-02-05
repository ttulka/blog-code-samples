package com.ttulka.blog.txevents.payment.event;

import com.ttulka.blog.txevents.payment.Payment;
import lombok.ToString;

@ToString(callSuper = true)
public class PaymentIssued extends PaymentEvent {

    public PaymentIssued(Payment payment) {
        super(payment);
    }
}
