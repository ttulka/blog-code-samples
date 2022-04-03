package com.ttulka.blog.orm;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    int ADULT_AGE = 21;

    Optional<Person> findByFamilyNameAndGivenNameAndBirthdateBefore(String familyName, String givenName, ZonedDateTime bornBefore);

    Optional<Person> findByPersonalNumber(String personalNumber);

    @EntityGraph(attributePaths = "parent")
    @Query("SELECT p FROM Person p WHERE p.personalNumber = ?1")
    Optional<Person> findByPersonalNumberWithParent(String personalNumber);

    default Optional<Person> findAdultByFullName(String givenName, String familyName) {
        return findByFamilyNameAndGivenNameAndBirthdateBefore(familyName, givenName, ZonedDateTime.now().minusYears(ADULT_AGE));
    }
}
