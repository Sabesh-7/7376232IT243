package com.affordmed.vehicle_maintence_scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for logging to external test server
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLogRequest {

    private String stack;
    private String level;

    @JsonProperty("package_")
    private String packageName;

    private String message;
}
