package com.ttulka.samples.ecommerce.product;

/**
 * Product API.
 */
public interface Product {

    Long id();

    String name();

    Double price();

    boolean deliverable();
}
