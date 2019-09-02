package com.ttulka.samples.threading;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BatchProcessing {

    private final int batchSize;
    private final JdbcTemplate jdbcTemplate;

    private final Collection<String> resultsBatch = new ConcurrentSkipListSet<>();
    private final AtomicInteger counter = new AtomicInteger();

    private volatile boolean finished = false;

    synchronized public void process(String result) {
        resultsBatch.add(result);

        if (finished || counter.incrementAndGet() >= batchSize) {
            load();
        }
    }

    synchronized public void finish() {
        finished = true;
        load();
    }

    synchronized private void load() {
        List<String> batch = new ArrayList<>(resultsBatch);
        resultsBatch.removeAll(batch);
        counter.set(0);

        jdbcTemplate.batchUpdate("INSERT INTO results (result) VALUES (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, batch.get(i));
            }

            @Override
            public int getBatchSize() {
                return batch.size();
            }
        });
    }
}
