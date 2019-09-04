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
class BatchLoaderThreadOwnData {

    private final int batchSize;
    private final JdbcTemplate jdbcTemplate;

    private boolean finished = false;

    private final Map<Long, ThreadOwnData> threadOwnDataMap = new ConcurrentHashMap<>();

    public void load(String result) {
        threadOwnData().resultsBatch.add(result);

        if (finished || threadOwnData().counter++ >= batchSize) {
            batchLoad(threadOwnData());
        }
    }

    public void finish() {
        finished = true;
        threadOwnDataMap.values().forEach(this::batchLoad);

    }

    private void batchLoad(ThreadOwnData data) {
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

    private ThreadOwnData threadOwnData() {
        return threadOwnDataMap.computeIfAbsent(Thread.currentThread().getId(), id -> new ThreadOwnData(batchSize));
    }

    static class ThreadOwnData {

        public final List<String> resultsBatch;
        public int counter = 0;

        public ThreadOwnData(int batchSize) {
            resultsBatch = new ArrayList<>(batchSize);
        }
    }
}
