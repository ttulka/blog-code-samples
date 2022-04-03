package com.ttulka.blog.orm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping("/{id}/parent")
    Optional<Person> getParent(@PathVariable String id) {
        return personRepository
                .findByPersonalNumber(id)
                .map(Person::getParent);
    }

    @PostMapping
    Person generatePersons() {
        var parent = generatePerson("Homer", "Simpson");
        parent.setBirthdate(ZonedDateTime.now().minusYears(50));
        parent = personRepository.save(parent);

        var child = generatePerson("Bart", "Simpson");
        child.setBirthdate(ZonedDateTime.now().minusYears(10));
        child.setParent(parent);
        child = personRepository.save(child);

        return child;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        e.printStackTrace();
        return e.toString();
    }

    private Person generatePerson(String givenName, String familyName) {
        var person = new Person();
        person.setPersonalNumber(UUID.randomUUID().toString());
        person.setGivenName(givenName);
        person.setFamilyName(familyName);
        return person;
    }
}
