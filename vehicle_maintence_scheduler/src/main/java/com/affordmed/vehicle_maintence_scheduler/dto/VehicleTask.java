package com.affordmed.vehicle_maintence_scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a vehicle maintenance task with duration and operational impact score.
 * This is the basic unit for the knapsack scheduling algorithm.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTask {
    
    /**
     * Unique identifier for the task.
     */
    private String taskID;
    
    /**
     * Duration of the maintenance task in hours.
     * Acts as the "weight" in the knapsack algorithm.
     */
    private int duration;
    
    /**
     * Operational impact score representing task importance.
     * Higher score = higher priority. Acts as the "value" in knapsack.
     */
    private int impact;
}
