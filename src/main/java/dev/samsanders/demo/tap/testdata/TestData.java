package dev.samsanders.demo.tap.testdata;

import java.time.Instant;

public record TestData(long id, String data, Instant createdAt) {
}
