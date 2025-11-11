package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public Counter helloCounter(MeterRegistry registry) {
        return Counter.builder("hello_requests_total")
                .description("Total /hello requests")
                .register(registry);
    }

    @RestController
    static class HelloController {
        private final Counter helloCounter;

        HelloController(Counter helloCounter) {
            this.helloCounter = helloCounter;
        }

        @GetMapping("/hello")
        public String hello() {
            helloCounter.increment();
            return "Hello from Spring Boot + Micrometer";
        }
    }
}