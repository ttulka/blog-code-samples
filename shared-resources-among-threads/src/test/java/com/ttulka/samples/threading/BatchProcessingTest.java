package com.ttulka.samples.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = BatchProcessingTest.TestConfig.class)
class BatchProcessingTest {

    private static final int COMPUTING_AMOUNT = 10_000;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @SneakyThrows
    void test() {
        final BatchProcessing batchProcessing = new BatchProcessing(100, jdbcTemplate);
        final ExecutorService executor = Executors.newCachedThreadPool();

        IntStream.range(0, COMPUTING_AMOUNT)
                .mapToObj(String::valueOf)
                .forEach(i -> executor.execute(() -> batchProcessing.process(i)));

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }

        batchProcessing.finish();

        assertEquals(COMPUTING_AMOUNT, (int) jdbcTemplate.queryForObject("SELECT COUNT(*) FROM results", Integer.class));
    }

    static class TestConfig {
    }
}
