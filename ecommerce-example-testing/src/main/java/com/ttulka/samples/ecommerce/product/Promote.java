package com.ttulka.samples.ecommerce.product;

import lombok.RequiredArgsConstructor;

public interface Promote {

    Product product(ToPromote toPromote);

    @RequiredArgsConstructor
    class ToPromote {

        public final String name;
        public final Double price;
    }
}
