package com.ttulka.blog.samples.solid;

import java.util.Objects;

abstract class BaseEmployee implements Employee {

    private final String personalId;
    private final String firstName;
    private final String lastName;

    /**
     * @param personalId the personal ID
     * @param firstName  the first name
     * @param lastName   the last name
     */
    public BaseEmployee(String personalId, String firstName, String lastName) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public final String personalId() {
        return personalId;
    }

    @Override
    public final String fullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(Employee.class)) {
            return false;
        }
        Employee other = (Employee) o;
        return personalId.equals(other.personalId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId);
    }
}
