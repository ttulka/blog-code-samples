package com.ttulka.samples.threading;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = BatchLoaderTest.TestConfig.class)
class BatchLoaderTest {

    private static final int AMOUNT = 1_000_000;
    private static final int BATCH_SIZE = 10_000;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ParameterizedTest
//    @ValueSource(ints = {1, 2, 4})
    @ValueSource(ints = {4})
    @SneakyThrows
    void instancePerThread_amountOfLoadedEqualsExtracted(int threads) {
        final Map<Long, BatchLoaderUnsafe> batchLoaders = new ConcurrentHashMap<>();
        final ExecutorService executor = Executors.newFixedThreadPool(threads);

        long start = System.currentTimeMillis();

        IntStream.range(0, AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(
                        () -> batchLoaders.computeIfAbsent(Thread.currentThread().getId(),
                                                           id -> new BatchLoaderUnsafe(BATCH_SIZE, jdbcTemplate))
                                .load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoaders.values().forEach(BatchLoaderUnsafe::finish);

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT result) FROM results", Integer.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    @SneakyThrows
    void threadOwnData_amountOfLoadedEqualsExtracted(int threads) {
        final BatchLoaderThreadOwnData batchLoader = new BatchLoaderThreadOwnData(BATCH_SIZE, jdbcTemplate);
        final ExecutorService executor = Executors.newFixedThreadPool(threads);

        long start = System.currentTimeMillis();

        IntStream.range(0, AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(() -> batchLoader.load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoader.finish();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT result) FROM results", Integer.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    @SneakyThrows
    void synchronized_amountOfLoadedEqualsExtracted(int threads) {
        final BatchLoaderSync batchLoader = new BatchLoaderSync(BATCH_SIZE, jdbcTemplate);
        final ExecutorService executor = Executors.newFixedThreadPool(threads);

        long start = System.currentTimeMillis();

        IntStream.range(0, AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(() -> batchLoader.load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoader.finish();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT result) FROM results", Integer.class));
    }

    @Test
    @SneakyThrows
    void singleThread_amountOfLoadedEqualsExtracted() {
        final BatchLoaderUnsafe batchLoader = new BatchLoaderUnsafe(BATCH_SIZE, jdbcTemplate);
        final ExecutorService executor = Executors.newFixedThreadPool(1);

        long start = System.currentTimeMillis();

        IntStream.range(0, AMOUNT)    // extract
                .mapToObj(String::valueOf)      // transform
                .forEach(i -> executor.execute(() -> batchLoader.load(i))); // load

        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchLoader.finish();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

        assertEquals(AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT result) FROM results", Integer.class));
    }

    @BeforeEach
    void eraseTable() {
        jdbcTemplate.execute("DELETE FROM results");
    }

    static class TestConfig {
    }
}
