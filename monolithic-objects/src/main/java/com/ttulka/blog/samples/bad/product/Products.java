package com.ttulka.blog.samples.bad.product;

import java.util.Collection;

public interface Products {

    Collection<Product> listAll();

    Product findById(ProductId id);
}
