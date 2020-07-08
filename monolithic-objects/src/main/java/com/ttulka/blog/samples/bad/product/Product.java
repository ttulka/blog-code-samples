package com.ttulka.blog.samples.bad.product;

public interface Product {

    ProductId id();
    Title title();
    Money price();
    Availability availability();

    void changeTitle(Title newTitle);
    void changePrice(Money newPrice);
}
