package com.ttulka.blog.samples.solid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class PaycheckTest {

    @Test
    void manager_earns_2000() {
        Paycheck paycheck = new Paycheck(
                new Manager("01", "First", "Last",
                            new EmployeeRegistryInMem())
        );
        assertThat(paycheck.amount()).isCloseTo(2000.00, within(0.0));
    }

    @Test
    void developer_earns_1000() {
        Paycheck paycheck = new Paycheck(
                new Developer("01", "First", "Last",
                              new EmployeeRegistryInMem())
        );
        assertThat(paycheck.amount()).isCloseTo(1000.00, within(0.0));
    }
}
