package com.ttulka.blog.samples.account;

import java.time.ZonedDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Repository
interface UserAccountEntries extends CrudRepository<UserAccountEntries.Entry, Long> {

    Optional<Entry> findByUsername(String username);

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    class Entry {
        @Id
        @GeneratedValue
        @Column
        public Long id;
        @Column(unique = true)
        public String username;
        @Column
        public String email;
        @Column
        public byte[] encryptedPassword;
        @Column
        public String salt;
        @Column
        public ZonedDateTime lastLoggedIn;
    }
}
