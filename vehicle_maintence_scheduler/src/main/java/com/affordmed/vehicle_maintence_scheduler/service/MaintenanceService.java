package com.affordmed.vehicle_maintence_scheduler.service;

import com.affordmed.vehicle_maintence_scheduler.dto.Depot;
import com.affordmed.vehicle_maintence_scheduler.dto.ScheduleResponse;
import com.affordmed.vehicle_maintence_scheduler.dto.VehicleTask;
import com.affordmed.vehicle_maintence_scheduler.util.KnapsackAlgorithm;
import com.affordmed.vehicle_maintence_scheduler.util.KnapsackAlgorithm.KnapsackResult;
import com.affordmed.vehicle_maintence_scheduler.util.Log;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for vehicle maintenance scheduling logic.
 * Orchestrates the process of fetching depot/vehicle data and applying
 * the knapsack algorithm to determine optimal task scheduling.
 */
@Service
@RequiredArgsConstructor
public class MaintenanceService {
    
    private final ExternalApiService externalApiService;
    
    /**
     * Generates an optimal maintenance schedule for a specific depot.
     * 
     * Algorithm:
     * 1. Fetch all depots and find the requested depot's mechanic hours
     * 2. Fetch all available vehicle tasks
     * 3. Apply 0/1 knapsack algorithm to maximize operational impact
     * 4. Return the schedule with selected tasks
     * 
     * @param depotID The ID of the depot to schedule for
     * @return ScheduleResponse containing selected tasks and metrics
     * @throws IllegalArgumentException if depot not found
     */
    public ScheduleResponse scheduleMaintenanceTasks(int depotID) {
        
        Log.info("service", "Starting maintenance scheduling for depot " + depotID);
        
        try {
            // Fetch depot information
            List<Depot> depots = externalApiService.fetchDepots();
            
            Depot targetDepot = depots.stream()
                    .filter(d -> d.getId() == depotID)
                    .findFirst()
                    .orElse(null);
            
            if (targetDepot == null) {
                Log.error("service", "Depot with ID " + depotID + " not found");
                throw new IllegalArgumentException("Depot not found: " + depotID);
            }
            
            Log.debug("service", "Found depot " + depotID + " with " + 
                    targetDepot.getMechanicHours() + " mechanic hours available");
            
            // Fetch all vehicle tasks
            List<VehicleTask> allTasks = externalApiService.fetchVehicles();
            
            Log.debug("service", "Total available tasks: " + allTasks.size());
            
            // Apply knapsack algorithm
            Log.info("service", "Applying 0/1 knapsack algorithm with capacity " + 
                    targetDepot.getMechanicHours());
            
            KnapsackResult result = KnapsackAlgorithm.solve(
                    allTasks,
                    targetDepot.getMechanicHours()
            );
            
            Log.info("service", "Knapsack completed: selected " + 
                    result.getSelectedTasks().size() + " tasks with total impact " + 
                    result.getTotalImpact() + " and duration " + result.getTotalDuration());
            
            // Build response
            ScheduleResponse response = new ScheduleResponse(
                    depotID,
                    targetDepot.getMechanicHours(),
                    result.getSelectedTasks(),
                    result.getTotalDuration(),
                    result.getTotalImpact(),
                    result.getRemainingCapacity()
            );
            
            Log.info("service", "Successfully generated schedule for depot " + depotID);
            
            return response;
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            Log.fatal("service", "Unexpected error during scheduling: " + e.getMessage());
            throw new RuntimeException("Failed to schedule maintenance tasks", e);
        }
    }
    
    /**
     * Retrieves all available depots without scheduling.
     * Useful for frontend to display available depot options.
     * 
     * @return List of all available depots
     */
    public List<Depot> getAllDepots() {
        Log.info("service", "Fetching all available depots");
        try {
            List<Depot> depots = externalApiService.fetchDepots();
            Log.info("service", "Retrieved " + depots.size() + " depots");
            return depots;
        } catch (Exception e) {
            Log.error("service", "Error fetching all depots: " + e.getMessage());
            throw new RuntimeException("Failed to fetch depots", e);
        }
    }
}
