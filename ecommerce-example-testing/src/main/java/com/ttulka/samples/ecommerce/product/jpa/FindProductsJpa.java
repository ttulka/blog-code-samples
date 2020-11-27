package com.ttulka.samples.ecommerce.product.jpa;

import java.util.Collection;
import java.util.stream.Collectors;

import com.ttulka.samples.ecommerce.product.FindProducts;
import com.ttulka.samples.ecommerce.product.Product;
import com.ttulka.samples.ecommerce.product.PromoProduct;
import com.ttulka.samples.ecommerce.product.StandardProduct;
import com.ttulka.samples.ecommerce.product.jpa.entity.ProductEntity;
import com.ttulka.samples.ecommerce.product.jpa.repository.ProductRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindProductsJpa implements FindProducts {

    private final @NonNull ProductRepository repository;

    @Override
    public Collection<Product> standards() {
        return repository.findByType(ProductEntity.Type.STANDARD).stream()
                .map(entry -> new StandardProduct(
                        entry.id, entry.name, entry.price
                ))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Product> promos() {
        return repository.findByType(ProductEntity.Type.PROMO).stream()
                .map(entry -> new PromoProduct(
                        entry.id, entry.name, entry.price
                ))
                .collect(Collectors.toSet());
    }
}
