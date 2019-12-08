package com.ttulka.blog.samples.solid;

import java.util.HashMap;
import java.util.Map;

class EmployeeRegistry {

    private final static Map<String, Employee> map = new HashMap<>();

    /**
     * Registers the employee.
     * @param employee the employee
     */
    public void register(Employee employee) {
        map.put(employee.personalId, employee);
    }

    /**
     * Is the employee already registered?
     * @param employee the employee
     * @return true if already registered, otherwise false
     */
    public boolean isRegistered(Employee employee) {
        return map.containsKey(employee.personalId);
    }
}
