package com.ttulka.blog.samples.solid;

public class Paycheck {

    private final PayedEmployee employee;

    /**
     * @param employee the employee the paycheck is for
     */
    public Paycheck(PayedEmployee employee) {
        this.employee = employee;
    }

    /**
     * Returns the amount to be paid.
     * @return the amount
     */
    public double amount() {
        return employee.salary();
    }
}
