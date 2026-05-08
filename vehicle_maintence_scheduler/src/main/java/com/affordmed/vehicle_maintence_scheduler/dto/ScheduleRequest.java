package com.affordmed.vehicle_maintence_scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for scheduling maintenance tasks at a specific depot.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
    
    /**
     * Depot ID for which to schedule maintenance tasks.
     */
    private int depotID;
}
