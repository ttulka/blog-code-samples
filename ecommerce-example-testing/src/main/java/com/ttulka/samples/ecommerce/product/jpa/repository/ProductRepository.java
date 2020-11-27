package com.ttulka.samples.ecommerce.product.jpa.repository;

import java.util.Collection;

import com.ttulka.samples.ecommerce.product.jpa.entity.ProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Collection<ProductEntity> findByType(ProductEntity.Type type);
}
