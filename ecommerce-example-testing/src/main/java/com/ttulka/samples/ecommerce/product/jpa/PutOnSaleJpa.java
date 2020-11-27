package com.ttulka.samples.ecommerce.product.jpa;

import com.ttulka.samples.ecommerce.product.Product;
import com.ttulka.samples.ecommerce.product.PutOnSale;
import com.ttulka.samples.ecommerce.product.StandardProduct;
import com.ttulka.samples.ecommerce.product.jpa.entity.ProductEntity;
import com.ttulka.samples.ecommerce.product.jpa.repository.ProductRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PutOnSaleJpa implements PutOnSale {

    private final @NonNull ProductRepository repository;

    @Override
    public Product product(@NonNull ToPutOnSale toPutOnSale) {
        var entity = new ProductEntity();
        entity.type = ProductEntity.Type.STANDARD;
        entity.name = toPutOnSale.name;
        entity.price = toPutOnSale.price;

        var saved = repository.save(entity);

        return new StandardProduct(
                saved.id, saved.name, saved.price);
    }
}
