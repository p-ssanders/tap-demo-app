package dev.samsanders.demo.tap.testdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class TestDataRepository implements Repository<TestData, Long> {

    private static final Logger logger = LoggerFactory.getLogger(TestDataRepository.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TestDataRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    TestData create(TestData testData) {
        String insertQuery = "insert into test_data (data, created_at) values (:testData, :createdAt)";
        Map<String, Object> values = new HashMap<>();
        values.put("testData", testData.data());
        Instant createdAt = Instant.now();
        values.put("createdAt", new Timestamp(createdAt.toEpochMilli()));

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertQuery, new MapSqlParameterSource(values), generatedKeyHolder);

        return new TestData(((BigInteger) generatedKeyHolder.getKey()).longValue(), testData.data(), createdAt);
    }

    long count() {
        return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject("select count(1) from test_data", Long.class);
    }

    public Optional<TestData> read(long id) {
        String readQuery = "select id, data, created_at from test_data where id = :id";
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);

        List<TestData> testDataList = namedParameterJdbcTemplate.query(readQuery, values, new RowMapper<TestData>() {
            @Override
            public TestData mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String data = rs.getString("data");
                Timestamp created_at = rs.getTimestamp("created_at");

                return new TestData(id, data, created_at.toInstant());
            }
        });

        return testDataList.stream().findFirst();
    }
}
