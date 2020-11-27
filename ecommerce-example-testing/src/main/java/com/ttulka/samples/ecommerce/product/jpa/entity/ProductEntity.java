package com.ttulka.samples.ecommerce.product.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ttulka.samples.ecommerce.common.jpa.AbstractEntity;

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends AbstractEntity {

    public enum Type {
        STANDARD, PROMO
    }

    @Enumerated(EnumType.STRING)
    public Type type;

    public String name;

    public Double price;
}
