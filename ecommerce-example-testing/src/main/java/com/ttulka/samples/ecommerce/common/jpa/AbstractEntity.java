package com.ttulka.samples.ecommerce.common.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@MappedSuperclass
@Getter
@EqualsAndHashCode
public abstract class AbstractEntity implements Serializable, Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public boolean isNew() {
        return null == getId();
    }
}
