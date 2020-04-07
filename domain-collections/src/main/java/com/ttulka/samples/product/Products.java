package com.ttulka.samples.product;

import java.util.stream.Stream;

public interface Products {

    Products sorted(SortBy by);

    Stream<Product> asStream();

    enum SortBy {
        TITLE, PRICE
    }
}
