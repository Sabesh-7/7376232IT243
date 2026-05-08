package com.affordmed.vehicle_maintence_scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a maintenance depot with available mechanic hours.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depot {
    
    /**
     * Unique identifier for the depot.
     */
    private int id;
    
    /**
     * Total mechanic hours available per day at this depot.
     * This acts as the "capacity" in the knapsack algorithm.
     */
    private int mechanicHours;
}
