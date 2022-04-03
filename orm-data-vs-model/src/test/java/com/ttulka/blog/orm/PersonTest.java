package com.ttulka.blog.orm;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class PersonTest {

    @Autowired
    private PersonRepository personRepository;

    private Person child;
    private Person adult;

    @BeforeEach
    void prepareData() {
        adult = new Person();
        adult.setFamilyName("White");
        adult.setGivenName("Walter");
        adult.setPersonalNumber("SENIOR");
        adult.setBirthdate(ZonedDateTime.now().minusYears(PersonRepository.ADULT_AGE).minusDays(1));
        adult = personRepository.save(adult);

        child = new Person();
        child.setFamilyName("White");
        child.setGivenName("Walter");
        child.setPersonalNumber("JUNIOR");
        child.setBirthdate(ZonedDateTime.now().minusYears(PersonRepository.ADULT_AGE - 1));
        child.setParent(adult);
        child = personRepository.save(child);
    }

    @AfterEach
    void cleanUp() {
        personRepository.deleteAll();
    }

    @Test
    void only_adults_are_found() {
        var userOpt = personRepository.findAdultByFullName("Walter", "White");
        assertAll(
                () -> assertThat(userOpt.isPresent()).isTrue(),
                () -> assertThat(userOpt.get().getPersonalNumber()).isEqualTo("SENIOR")
        );
    }

    @Test
    void fails_to_load_lazy_attributes_outside_a_transaction() {
        assertThrows(LazyInitializationException.class, () -> {
            var person = personRepository.findByPersonalNumber("JUNIOR").get();
            person.getParent().getBirthdate();    // parent is LAZY
        });
    }

    @Test
    void loads_lazy_attributes_in_a_entity_graph() {
        var person = personRepository.findByPersonalNumberWithParent("JUNIOR").get();
        person.getParent().getBirthdate();
    }
}
