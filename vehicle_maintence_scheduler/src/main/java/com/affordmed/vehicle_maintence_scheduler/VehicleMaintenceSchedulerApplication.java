package com.affordmed.vehicle_maintence_scheduler;

import com.affordmed.vehicle_maintence_scheduler.service.LoggingService;
import com.affordmed.vehicle_maintence_scheduler.util.Log;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class VehicleMaintenceSchedulerApplication {

    private final LoggingService loggingService;

    public static void main(String[] args) {
        SpringApplication.run(
                VehicleMaintenceSchedulerApplication.class,
                args
        );
    }

    @PostConstruct
    public void init() {
        Log.setLoggingService(loggingService);
    }
}
