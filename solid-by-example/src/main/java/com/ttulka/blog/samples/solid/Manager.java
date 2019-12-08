package com.ttulka.blog.samples.solid;

public class Manager extends BaseEmployee implements PayedEmployee {

    public Manager(String personalId, String firstName, String lastName) {
        super(personalId, firstName, lastName);
    }

    @Override
    public double salary() {
        return 2000.0;
    }
}
