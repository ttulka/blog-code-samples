package com.ttulka.samples.ecommerce.product.jpa;

import com.ttulka.samples.ecommerce.product.Product;
import com.ttulka.samples.ecommerce.product.PromoProduct;
import com.ttulka.samples.ecommerce.product.Promote;
import com.ttulka.samples.ecommerce.product.jpa.entity.ProductEntity;
import com.ttulka.samples.ecommerce.product.jpa.repository.ProductRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PromoteJpa implements Promote {

    private final @NonNull ProductRepository repository;

    @Override
    public Product product(@NonNull ToPromote toPromote) {
        var entity = new ProductEntity();
        entity.type = ProductEntity.Type.PROMO;
        entity.name = toPromote.name;
        entity.price = toPromote.price;

        var saved = repository.save(entity);

        return new PromoProduct(
                saved.id, saved.name, saved.price);
    }
}
