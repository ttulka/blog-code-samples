package com.ttulka.blog.samples.bad.product;

import java.util.Collection;

public interface Products {

    Collection<Product> findAll();

    Product findById(ProductId id);
}
