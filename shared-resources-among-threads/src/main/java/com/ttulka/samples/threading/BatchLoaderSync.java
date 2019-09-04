package com.ttulka.samples.threading;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

class BatchLoaderSync {

    private final int batchSize;
    private final JdbcTemplate jdbcTemplate;

    private final List<String> resultsBatch;
    private int counter = 0;

    private volatile boolean finished = false;

    public BatchLoaderSync(int batchSize, JdbcTemplate jdbcTemplate) {
        this.batchSize = batchSize;
        this.jdbcTemplate = jdbcTemplate;
        this.resultsBatch = new ArrayList<>(batchSize);
    }

    synchronized public void load(String result) {
        resultsBatch.add(result);

        if (finished || counter++ >= batchSize) {
            batchLoad();
        }
    }

    public void finish() {
        finished = true;
        batchLoad();
    }

    synchronized private void batchLoad() {
        jdbcTemplate.batchUpdate("INSERT INTO results (result) VALUES (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, resultsBatch.get(i));
            }

            @Override
            public int getBatchSize() {
                return resultsBatch.size();
            }
        });

        // expensive work
        resultsBatch.forEach(s -> new ExpensiveWorker(s).work());

        resultsBatch.clear();
        counter = 0;
    }
}
