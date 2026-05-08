package com.affordmed.vehicle_maintence_scheduler.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response wrapper for vehicle tasks from external API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private List<VehicleTask> vehicles;
}
