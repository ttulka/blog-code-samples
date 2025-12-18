package com.ttulka.blog.outboxwodb.payment;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Payment {

    String reference;
    BigDecimal amount;
}
