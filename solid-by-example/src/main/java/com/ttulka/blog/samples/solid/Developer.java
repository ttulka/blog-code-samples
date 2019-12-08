package com.ttulka.blog.samples.solid;

public class Developer extends Employee implements PayedEmployee {

    public Developer(String personalId, String firstName, String lastName, EmployeeRegistry registry) {
        super(personalId, firstName, lastName, registry);
    }

    @Override
    public double salary() {
        return 1000.0;
    }
}
