package com.ttulka.blog.samples.solid;

import java.util.HashSet;
import java.util.Set;

class EmployeeRegistryInMem implements EmployeeRegistry {

    private final static Set<Employee> mem = new HashSet<>();

    @Override
    public void register(Employee employee) {
        mem.add(employee);
    }

    @Override
    public boolean isRegistered(Employee employee) {
        return mem.contains(employee);
    }
}
