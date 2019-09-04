package com.ttulka.samples.threading;

import java.security.MessageDigest;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
class ExpensiveWorker {

    private final String input;

    @SneakyThrows
    public void work() {
        for (int i = 0; i < 100; i++) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            md.digest();
        }
    }
}
