package com.ttulka.samples.customer.jpa;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.repository.CrudRepository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

interface CustomerRepository extends CrudRepository<CustomerRepository.CustomerEntry, UUID> {

    Optional<CustomerEntry> findByEmail(String email);

    @Entity
    @Table(name = "customers_jpa")
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
    @ToString
    class CustomerEntry {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        public UUID id;
        public String firstName;
        public String lastName;
        public String email;
    }
}
