package com.ttulka.blog.samples.solid;

public class Volunteer extends Employee {

    public Volunteer(String personalId, String firstName, String lastName, EmployeeRegistry registry) {
        super(personalId, firstName, lastName, registry);
    }
}
