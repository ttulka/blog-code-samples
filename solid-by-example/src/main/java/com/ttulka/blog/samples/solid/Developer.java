package com.ttulka.blog.samples.solid;

public class Developer extends BaseEmployee implements PayedEmployee {

    public Developer(String personalId, String firstName, String lastName) {
        super(personalId, firstName, lastName);
    }

    @Override
    public double salary() {
        return 1000.0;
    }
}
