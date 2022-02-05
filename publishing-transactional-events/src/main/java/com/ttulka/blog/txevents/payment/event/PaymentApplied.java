package com.ttulka.blog.txevents.payment.event;

import com.ttulka.blog.txevents.payment.Payment;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class PaymentApplied {

    public PaymentApplied(Payment payment) {
        this.reference = payment.getReference();
        this.at = ZonedDateTime.now();
    }

    private final String reference;
    private final ZonedDateTime at;
}
