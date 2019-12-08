package com.ttulka.blog.samples.solid;

public class Volunteer extends Employee {

    public Volunteer(String personalId, String firstName, String lastName) {
        super(personalId, firstName, lastName);
    }

    @Override
    public double salary() {
        throw new RuntimeException("No salary for volunteers!");
    }
}
