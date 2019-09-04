package com.ttulka.samples.threading;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BatchLoader {

    private final int batchSize;
    private final JdbcTemplate jdbcTemplate;

    private boolean finished = false;

    private final Map<Long, ThreadsData> threadsData = new ConcurrentHashMap<>();

    public void load(String result) {
        data().resultsBatch.add(result);

        if (finished || data().counter++ >= batchSize) {
            batchLoad(data());
        }
    }

    public void finish() {
        finished = true;
        threadsData.values().forEach(data -> {
            batchLoad(data);
        });

    }

    private void batchLoad(ThreadsData data) {
        jdbcTemplate.batchUpdate("INSERT INTO results (result) VALUES (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.resultsBatch.get(i));
            }

            @Override
            public int getBatchSize() {
                return data.resultsBatch.size();
            }
        });

        // expensive work
        data.resultsBatch.forEach(s -> new ExpensiveWorker(s).work());

        data.counter = 0;
        data.resultsBatch.clear();
    }

    private ThreadsData data() {
        return threadsData.computeIfAbsent(Thread.currentThread().getId(), id -> new ThreadsData(batchSize));
    }

    static class ThreadsData {

        public final List<String> resultsBatch;
        public int counter = 0;

        public ThreadsData(int batchSize) {
            resultsBatch = new ArrayList<>(batchSize);
        }
    }
}
