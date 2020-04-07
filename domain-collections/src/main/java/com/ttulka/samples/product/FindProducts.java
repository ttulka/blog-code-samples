package com.ttulka.samples.product;

import java.util.List;

public interface FindProducts {

    List<Product> cheaperThan(Money money);
}
