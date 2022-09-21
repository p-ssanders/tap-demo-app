package dev.samsanders.demo.tap;

import dev.samsanders.demo.tap.testdata.TestDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication
public class TapDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TapDemoAppApplication.class, args);
	}

	@Bean
	TestDataRepository testDataRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		return new TestDataRepository(namedParameterJdbcTemplate);
	}
}
