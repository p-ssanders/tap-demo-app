package dev.samsanders.demo.tap.testdata;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class TestDataController {

    private final TestDataRepository testDataRepository;
    private final StreamBridge streamBridge;

    public TestDataController(TestDataRepository testDataRepository, StreamBridge streamBridge) {
        this.testDataRepository = testDataRepository;
        this.streamBridge = streamBridge;
    }

    @GetMapping
    Map<String, Long> getTestDataCount() {
        return Collections.singletonMap("count", testDataRepository.count());
    }

    @PostMapping
    ResponseEntity<Void> createTestData() {
        TestData testData = new TestData(0, UUID.randomUUID().toString(), null);
        testData = testDataRepository.create(testData);
        UriComponents location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(testData.id());

        streamBridge.send("test-data-created", Collections.singletonMap("URI", location.toString()));

        return ResponseEntity.created(location.toUri()).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getTestData(@PathVariable long id) {
        Optional<TestData> testData = testDataRepository.read(id);

        if (testData.isPresent())
            return ResponseEntity.ok(testData.get());
        else
            return ResponseEntity.notFound().build();
    }

}
