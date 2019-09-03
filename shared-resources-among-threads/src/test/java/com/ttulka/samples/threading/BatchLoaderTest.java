package com.ttulka.samples.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = BatchLoaderTest.TestConfig.class)
class BatchLoaderTest {

    private static final int COMPUTING_AMOUNT = 1_000_000;
    private static final int BATCH_SIZE = 10_000;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @SneakyThrows
    void threadOwnData_amountOfLoadedEqualsExtracted() {
        final BatchLoader batchLoader = new BatchLoader(BATCH_SIZE, jdbcTemplate);
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

        IntStream.range(0, COMPUTING_AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(() -> batchLoader.load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoader.finish();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(COMPUTING_AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM results", Integer.class));
    }

    @Test
    @SneakyThrows
    void synchronized_amountOfLoadedEqualsExtracted() {
        final BatchLoaderSync batchLoader = new BatchLoaderSync(BATCH_SIZE, jdbcTemplate);
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

        IntStream.range(0, COMPUTING_AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(() -> batchLoader.load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoader.finish();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(COMPUTING_AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM results", Integer.class));
    }

    @Test
    @SneakyThrows
    void singleThread_amountOfLoadedEqualsExtracted() {
        final BatchLoaderUnsafe batchLoader = new BatchLoaderUnsafe(BATCH_SIZE, jdbcTemplate);
        final ExecutorService executor = Executors.newFixedThreadPool(1);

        long start = System.currentTimeMillis();

        IntStream.range(0, COMPUTING_AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(() -> batchLoader.load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoader.finish();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(COMPUTING_AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM results", Integer.class));
    }

    @BeforeEach
    void eraseTable() {
        jdbcTemplate.execute("DELETE FROM results");
    }

    static class TestConfig {
    }
}
