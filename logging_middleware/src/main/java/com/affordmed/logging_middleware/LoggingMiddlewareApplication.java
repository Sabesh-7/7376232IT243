package com.affordmed.logging_middleware;

import com.affordmed.logging_middleware.service.LoggingService;
import com.affordmed.logging_middleware.util.Log;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LoggingMiddlewareApplication {

    private final LoggingService loggingService;

    public static void main(String[] args) {
        SpringApplication.run(
                LoggingMiddlewareApplication.class,
                args
        );
    }

    @PostConstruct
    public void init() {

        Log.setLoggingService(loggingService);
    }
}