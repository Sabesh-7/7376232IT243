package com.affordmed.vehicle_maintence_scheduler.service;

import com.affordmed.vehicle_maintence_scheduler.dto.Depot;
import com.affordmed.vehicle_maintence_scheduler.dto.DepotResponse;
import com.affordmed.vehicle_maintence_scheduler.dto.VehicleResponse;
import com.affordmed.vehicle_maintence_scheduler.dto.VehicleTask;
import com.affordmed.vehicle_maintence_scheduler.util.Log;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service for fetching depot and vehicle data from the test server APIs.
 * Handles external API communication with comprehensive logging.
 */
@Service
@RequiredArgsConstructor
public class ExternalApiService {
    
    private final RestTemplate restTemplate;
    
    @Value("${affordmed.api.base-url}")
    private String baseUrl;
    
    @Value("${affordmed.access-token}")
    private String accessToken;
    
    /**
     * Fetches all available depots from the test server.
     * Each depot has a specified number of mechanic hours available per day.
     * 
     * @return List of available depots
     * @throws RuntimeException if API call fails
     */
    public List<Depot> fetchDepots() {
        try {
            String url = baseUrl + "/depots";
            
            Log.info("service", "Fetching depot list from evaluation service");
            
            DepotResponse response = makeGetRequest(url, DepotResponse.class);
            
            if (response == null || response.getDepots() == null) {
                Log.error("service", "Received null response from depots API");
                throw new RuntimeException("Invalid depot response from server");
            }
            
            Log.info("service", "Successfully fetched " + response.getDepots().size() + " depots");
            
            return response.getDepots();
            
        } catch (RestClientException e) {
            Log.error("service", "Error fetching depots: " + e.getMessage());
            throw new RuntimeException("Failed to fetch depots from evaluation service", e);
        }
    }
    
    /**
     * Fetches all vehicle maintenance tasks from the test server.
     * Each task has a duration (hours) and operational impact score.
     * 
     * @return List of available vehicle maintenance tasks
     * @throws RuntimeException if API call fails
     */
    public List<VehicleTask> fetchVehicles() {
        try {
            String url = baseUrl + "/vehicles";
            
            Log.info("service", "Fetching vehicle maintenance tasks from evaluation service");
            
            VehicleResponse response = makeGetRequest(url, VehicleResponse.class);
            
            if (response == null || response.getVehicles() == null) {
                Log.error("service", "Received null response from vehicles API");
                throw new RuntimeException("Invalid vehicles response from server");
            }
            
            Log.info("service", "Successfully fetched " + response.getVehicles().size() + " vehicle tasks");
            
            return response.getVehicles();
            
        } catch (RestClientException e) {
            Log.error("service", "Error fetching vehicles: " + e.getMessage());
            throw new RuntimeException("Failed to fetch vehicles from evaluation service", e);
        }
    }
    
    /**
     * Generic method to make GET requests with bearer token authentication.
     * 
     * @param url The API endpoint URL
     * @param responseType The expected response type class
     * @return The parsed response
     */
    private <T> T makeGetRequest(String url, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                responseType
        );
        
        return response.getBody();
    }
}
