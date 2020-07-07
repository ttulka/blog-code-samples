package com.ttulka.blog.samples.monolithic;

final class Title {

    private final String value;

    public Title(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Cannot be empty");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }
}
