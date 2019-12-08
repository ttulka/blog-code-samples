package com.ttulka.blog.samples.solid;

import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) {
        Employee manager = new Manager("000", "Monty", "Burns");
        Employee developer = new Developer("001", "Martin", "Prince");
        Employee volunteer = new Volunteer("003", "Lisa", "Simpson");

        manager.register();
        developer.register();
        volunteer.register();

        Stream.of(manager, developer, volunteer)
                .map(Employee::fullName)
                .forEach(System.out::println);

        Paycheck paycheck1 = new Paycheck(manager);
        Paycheck paycheck2 = new Paycheck(developer);
        Paycheck paycheck3 = new Paycheck(volunteer);

        double total = Stream.of(paycheck1, paycheck2, paycheck3)
                .mapToDouble(Paycheck::amount)
                .sum();

        System.out.println(String.format("Total to pay: $ %.2f", total));
    }
}
