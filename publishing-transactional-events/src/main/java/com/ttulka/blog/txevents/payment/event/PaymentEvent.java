package com.ttulka.blog.txevents.payment.event;

import com.ttulka.blog.txevents.payment.Payment;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
abstract class PaymentEvent {

    public PaymentEvent(Payment payment) {
        this.reference = payment.getReference();
        this.at = ZonedDateTime.now();
    }

    private final String reference;
    private final ZonedDateTime at;
}
