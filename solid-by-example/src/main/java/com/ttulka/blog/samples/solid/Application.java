package com.ttulka.blog.samples.solid;

import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) {
        EmployeeRegistry registry = new EmployeeRegistryInMem();

        Manager manager = new Manager("000", "Monty", "Burns", registry);
        Developer developer = new Developer("001", "Martin", "Prince", registry);
        Volunteer volunteer = new Volunteer("003", "Lisa", "Simpson", registry);

        manager.register();
        developer.register();
        volunteer.register();

        Stream.of(manager, developer, volunteer)
                .map(Employee::fullName)
                .forEach(System.out::println);

        Paycheck paycheck1 = new Paycheck(manager);
        Paycheck paycheck2 = new Paycheck(developer);

        double total = Stream.of(paycheck1, paycheck2)
                .mapToDouble(Paycheck::amount)
                .sum();

        System.out.println(String.format("Total to pay: $ %.2f", total));
    }
}
