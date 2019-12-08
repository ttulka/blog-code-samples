package com.ttulka.blog.samples.solid;

public interface EmployeeRegistry {

    /**
     * Registers the employee.
     * @param employee the employee
     */
    void register(Employee employee);

    /**
     * Is the employee already registered?
     * @param employee the employee
     * @return true if already registered, otherwise false
     */
    boolean isRegistered(Employee employee);
}
