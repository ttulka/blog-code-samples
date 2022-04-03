package com.ttulka.blog.txevents;

import org.junit.jupiter.api.Test;
import org.springframework.dao.ConcurrencyFailureException;

import java.util.Optional;

public class MyTest {

    @Test
    void mytest() {
        Optional.of(1).ifPresent(h -> {
            throw new ConcurrencyFailureException("haccp report already finished");
        });
        System.out.println("aaaaaaaaaaaaaaaaaa");
    }
}
