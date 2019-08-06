package com.ttulka.samples.textfile;

import java.nio.charset.Charset;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextFileTest {

    @Test
    void contentOnNonExistingFile_shouldThrowException() {
        assertThrows(RuntimeException.class,
                     () -> new TextFile(Path.of("/this/file/does/not/exist")).content());
    }

    @Test
    void contentOnUTF8File_shouldReturnContent() {
        assertEquals("Příliš žluťoučký kůň úpěl ďábelské ódy",
                     new TextFile(resourcePath("utf8.txt")).content());
    }

    @Test
    void contentOnCP1250File_shouldReturnContentInUtf8() {
        assertEquals("Příliš žluťoučký kůň úpěl ďábelské ódy",
                     new TextFile(resourcePath("cp1250.txt"), Charset.forName("cp1250")).content());
    }

    private Path resourcePath(String resourceName) {
        return Path.of(this.getClass().getResource("/textfile/" + resourceName)
                               .getPath()
                               .replaceFirst("/", ""));
    }
}
