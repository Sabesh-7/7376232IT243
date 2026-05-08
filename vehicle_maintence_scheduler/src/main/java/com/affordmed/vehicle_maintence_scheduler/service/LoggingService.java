package com.affordmed.vehicle_maintence_scheduler.service;

import com.affordmed.vehicle_maintence_scheduler.dto.VehicleLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoggingService {

    private final RestTemplate restTemplate;

    @Value("${affordmed.api.base-url}")
    private String baseUrl;

    @Value("${affordmed.access-token}")
    private String accessToken;

    public void log(
            String stack,
            String level,
            String packageName,
            String message
    ) {

        try {

            String url = baseUrl + "/logs";

            VehicleLogRequest requestBody = new VehicleLogRequest(
                    stack,
                    level,
                    packageName,
                    message
            );

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setBearerAuth(accessToken);

            HttpEntity<VehicleLogRequest> request =
                    new HttpEntity<>(requestBody, headers);

            restTemplate.postForEntity(
                    url,
                    request,
                    String.class
            );

        } catch (Exception ex) {
            // avoid recursive logging

        }
    }
}