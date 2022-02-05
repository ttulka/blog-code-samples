package com.ttulka.blog.txevents.payment.event;

import com.ttulka.blog.txevents.payment.Payment;
import lombok.ToString;

@ToString(callSuper = true)
public class PaymentApplied extends PaymentEvent {

    public PaymentApplied(Payment payment) {
        super(payment);
    }
}
