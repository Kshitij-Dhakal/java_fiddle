package org.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@SpringBootApplication
public class KafkaEventPublisherMain {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaEventPublisherMain.class);
    }

    public void sendMessage(String topic, String msg) {
        log.info("Publishing message on topic : {}, message : {}", topic, msg);
        kafkaTemplate.send(topic, msg);
        log.info("Published message on topic : {}, message : {}", topic, msg);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            for (int i = 0; i < 10; i++) {
                sendMessage("test", "Check 123");
            }
            System.exit(0);
        };
    }
}