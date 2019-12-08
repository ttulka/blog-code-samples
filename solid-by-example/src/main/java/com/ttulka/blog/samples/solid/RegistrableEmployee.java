package com.ttulka.blog.samples.solid;

public class RegistrableEmployee implements Employee {

    private final Employee employee;
    private final EmployeeRegistry registry;

    /**
     * @param employee the employee to register
     * @param registry the employee registry
     */
    public RegistrableEmployee(Employee employee, EmployeeRegistry registry) {
        this.employee = employee;
        this.registry = registry;
    }

    /**
     * Registers the employee.
     */
    public final void register() {
        registry.register(employee);
    }

    /**
     * Is the employee registered?
     *
     * @return true if the employee is registered, false otherwise
     */
    public final boolean isRegistered() {
        return registry.isRegistered(employee);
    }

    @Override
    public final String personalId() {
        return employee.personalId();
    }

    @Override
    public final String fullName() {
        return employee.fullName();
    }
}
