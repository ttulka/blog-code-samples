package com.ttulka.blog.samples.solid;

public class Manager extends Employee implements PayedEmployee {

    public Manager(String personalId, String firstName, String lastName, EmployeeRegistry registry) {
        super(personalId, firstName, lastName, registry);
    }

    @Override
    public double salary() {
        return 2000.0;
    }
}
