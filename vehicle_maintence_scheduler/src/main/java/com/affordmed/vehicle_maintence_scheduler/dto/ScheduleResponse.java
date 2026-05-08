package com.affordmed.vehicle_maintence_scheduler.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response containing optimal schedule of maintenance tasks.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    
    /**
     * Depot ID for which the schedule was generated.
     */
    private int depotID;
    
    /**
     * Available mechanic hours for this depot.
     */
    private int availableMechanicHours;
    
    /**
     * Optimal list of tasks to complete within the budget.
     */
    private List<VehicleTask> selectedTasks;
    
    /**
     * Total duration of selected tasks in hours.
     */
    private int totalDuration;
    
    /**
     * Total operational impact score achieved.
     */
    private int totalImpact;
    
    /**
     * Remaining unused mechanic hours.
     */
    private int remainingHours;
}
