package com.ttulka.myshop.product;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class Product {

    private final long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
}
