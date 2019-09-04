package com.ttulka.samples.threading;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

class BatchLoaderUnsafe {

    private final int batchSize;
    private final JdbcTemplate jdbcTemplate;

    private final List<String> resultsBatch;
    private int counter = 0;

    private boolean finished = false;

    public BatchLoaderUnsafe(int batchSize, JdbcTemplate jdbcTemplate) {
        this.batchSize = batchSize;
        this.jdbcTemplate = jdbcTemplate;
        this.resultsBatch = new ArrayList<>(batchSize);
    }

    public void load(String result) {
        resultsBatch.add(result);

        if (finished || counter++ >= batchSize) {
            batchLoad();
        }
    }

    public void finish() {
        finished = true;
        batchLoad();
    }

    private void batchLoad() {
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

        counter = 0;
        resultsBatch.clear();
    }
}
