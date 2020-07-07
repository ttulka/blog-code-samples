package com.ttulka.blog.samples.monolithic;

final class Money {

    private final double value;

    public Money(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Cannot be less than zero: " + value);
        }
        this.value = value;
    }

    public double value() {
        return value;
    }
}
