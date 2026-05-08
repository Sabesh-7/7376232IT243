package com.affordmed.vehicle_maintence_scheduler.controller;

import com.affordmed.vehicle_maintence_scheduler.dto.Depot;
import com.affordmed.vehicle_maintence_scheduler.dto.ScheduleRequest;
import com.affordmed.vehicle_maintence_scheduler.dto.ScheduleResponse;
import com.affordmed.vehicle_maintence_scheduler.service.MaintenanceService;
import com.affordmed.vehicle_maintence_scheduler.util.Log;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for vehicle maintenance scheduling endpoints.
 * Provides APIs to retrieve depot information and generate optimal maintenance schedules.
 */
@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceSchedulerController {
    
    private final MaintenanceService maintenanceService;
    
    /**
     * GET /api/maintenance/depots
     * 
     * Retrieves all available maintenance depots and their mechanic hour capacity.
     * This endpoint is useful for the frontend to show available depot options.
     * 
     * @return List of all depots with their IDs and available mechanic hours
     */
    @GetMapping("/depots")
    public ResponseEntity<List<Depot>> getAllDepots() {
        Log.info("controller", "Received GET request for all depots");
        
        try {
            List<Depot> depots = maintenanceService.getAllDepots();
            Log.debug("controller", "Returning " + depots.size() + " depots");
            return ResponseEntity.ok(depots);
        } catch (Exception e) {
            Log.error("controller", "Error fetching depots: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/maintenance/schedule/{depotId}
     * 
     * Generates optimal maintenance schedule for a specific depot using the knapsack algorithm.
     * Selects the maximum impact vehicle maintenance tasks that fit within the depot's
     * daily mechanic hour budget.
     * 
     * @param depotId ID of the depot to generate schedule for
     * @return ScheduleResponse containing selected tasks and scheduling metrics
     */
    @GetMapping("/schedule/{depotId}")
    public ResponseEntity<ScheduleResponse> generateSchedule(
            @PathVariable int depotId
    ) {
        Log.info("controller", "Received GET request for schedule of depot " + depotId);
        
        try {
            ScheduleResponse schedule = maintenanceService.scheduleMaintenanceTasks(depotId);
            Log.debug("controller", "Schedule generated successfully with " + 
                    schedule.getSelectedTasks().size() + " tasks");
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException e) {
            Log.warn("controller", "Invalid depot ID provided: " + depotId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            Log.error("controller", "Error generating schedule: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
    
    /**
     * POST /api/maintenance/schedule
     * 
     * Generates optimal maintenance schedule via POST request.
     * Alternative endpoint to GET /api/maintenance/schedule/{depotId}.
     * 
     * @param request ScheduleRequest containing depotID
     * @return ScheduleResponse containing selected tasks and scheduling metrics
     */
    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponse> scheduleViaPost(
            @RequestBody ScheduleRequest request
    ) {
        Log.info("controller", "Received POST request for schedule of depot " + request.getDepotID());
        
        try {
            int depotID = request.getDepotID();
            
            if (depotID <= 0) {
                Log.warn("controller", "Invalid depot ID in request: " + depotID);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            
            ScheduleResponse schedule = maintenanceService.scheduleMaintenanceTasks(depotID);
            Log.debug("controller", "Schedule generated successfully with " + 
                    schedule.getSelectedTasks().size() + " tasks");
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException e) {
            Log.warn("controller", "Invalid depot in request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            Log.error("controller", "Error processing schedule request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
