package com.ttulka.blog.samples.monolithic.product;

public interface Product {

    Title title();
    Money price();
    Availability availability();

    void changeTitle(Title newTitle);
    void changePrice(Money newPrice);
}
