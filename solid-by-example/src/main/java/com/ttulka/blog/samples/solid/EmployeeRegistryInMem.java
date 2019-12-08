package com.ttulka.blog.samples.solid;

import java.util.HashMap;
import java.util.Map;

class EmployeeRegistryInMem implements EmployeeRegistry {

    private final static Map<String, Employee> map = new HashMap<>();

    @Override
    public void register(Employee employee) {
        map.put(employee.personalId, employee);
    }

    @Override
    public boolean isRegistered(Employee employee) {
        return map.containsKey(employee.personalId);
    }
}
