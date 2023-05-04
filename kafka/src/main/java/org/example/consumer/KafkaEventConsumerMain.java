package org.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class KafkaEventConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(KafkaEventConsumerMain.class);
    }

    @KafkaListener(topics = "test", groupId = "foo")
    public void listenGroupFoo(String message) throws InterruptedException {
        log.info("Received Message in group foo: {}", message);
        Thread.sleep(100000);
        log.info("Processing completed");
    }
}