package com.ttulka.samples.textfile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFile {

    private final Path file;
    private final Charset charset;

    public TextFile(Path file) {
        this(file, Charset.forName("utf8"));
    }

    public TextFile(Path file, Charset charset) {
        this.file = file;
        this.charset = charset;
    }

    public final String content() {
        try {
            return Files.readString(file, charset);

        } catch (IOException e) {
            throw new RuntimeException("Could not read a file " + file, e);
        }
    }
}
