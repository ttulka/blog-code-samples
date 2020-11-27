package com.ttulka.samples.ecommerce.product;

import lombok.RequiredArgsConstructor;

public interface PutOnSale {

    Product product(ToPutOnSale toPutOnSale);

    @RequiredArgsConstructor
    class ToPutOnSale {

        public final String name;
        public final Double price;
    }
}
