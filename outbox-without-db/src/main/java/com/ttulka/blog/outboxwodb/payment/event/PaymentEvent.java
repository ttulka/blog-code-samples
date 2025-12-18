package com.ttulka.blog.outboxwodb.payment.event;

import com.ttulka.blog.outboxwodb.payment.Payment;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaymentEvent {

    public PaymentEvent(Payment payment) {
        this.reference = payment.getReference();
        this.at = ZonedDateTime.now();
    }

    private final String reference;
    private final ZonedDateTime at;
}
