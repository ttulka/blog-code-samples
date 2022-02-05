package com.ttulka.blog.txevents.payment;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Payment {

    private final String reference;
    private final BigDecimal amount;
}
