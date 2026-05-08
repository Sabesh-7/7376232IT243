package com.affordmed.vehicle_maintence_scheduler.util;

import com.affordmed.vehicle_maintence_scheduler.dto.VehicleTask;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the 0/1 Knapsack algorithm using Dynamic Programming.
 * 
 * Problem: Given a list of vehicle maintenance tasks with duration (weight) and
 * operational impact (value), select the maximum impact tasks that fit within
 * available mechanic hours (capacity).
 * 
 * Time Complexity: O(n * capacity)
 * Space Complexity: O(n * capacity)
 */
public class KnapsackAlgorithm {
    
    /**
     * Solves the 0/1 knapsack problem and returns selected tasks.
     * 
     * @param tasks List of vehicle maintenance tasks
     * @param capacity Available mechanic hours (budget)
     * @return List of selected tasks that maximize total impact within capacity
     */
    public static KnapsackResult solve(
            List<VehicleTask> tasks,
            int capacity
    ) {
        int n = tasks.size();
        
        // dp[i][w] represents maximum impact achievable using first i items with weight w
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Build the DP table bottom-up
        for (int i = 1; i <= n; i++) {
            VehicleTask currentTask = tasks.get(i - 1);
            int duration = currentTask.getDuration();
            int impact = currentTask.getImpact();
            
            for (int w = 0; w <= capacity; w++) {
                // Option 1: Don't include current task
                dp[i][w] = dp[i - 1][w];
                
                // Option 2: Include current task if it fits
                if (duration <= w) {
                    int includeValue = dp[i - 1][w - duration] + impact;
                    dp[i][w] = Math.max(dp[i][w], includeValue);
                }
            }
        }
        
        // Backtrack to find which items were selected
        List<VehicleTask> selectedTasks = backtrack(dp, tasks, capacity);
        
        int totalImpact = dp[n][capacity];
        int totalDuration = calculateTotalDuration(selectedTasks);
        
        return new KnapsackResult(
                selectedTasks,
                totalDuration,
                totalImpact,
                capacity - totalDuration
        );
    }
    
    /**
     * Backtracks through the DP table to find selected tasks.
     * 
     * @param dp The DP table
     * @param tasks List of all tasks
     * @param capacity The knapsack capacity
     * @return List of selected tasks
     */
    private static List<VehicleTask> backtrack(
            int[][] dp,
            List<VehicleTask> tasks,
            int capacity
    ) {
        List<VehicleTask> selected = new ArrayList<>();
        int i = tasks.size();
        int w = capacity;
        
        // Start from bottom-right corner and trace back
        while (i > 0 && w > 0) {
            // If value comes from including current item
            if (dp[i][w] != dp[i - 1][w]) {
                VehicleTask task = tasks.get(i - 1);
                selected.add(task);
                w -= task.getDuration();
            }
            i--;
        }
        
        return selected;
    }
    
    /**
     * Calculates total duration of selected tasks.
     */
    private static int calculateTotalDuration(List<VehicleTask> tasks) {
        return tasks.stream()
                .mapToInt(VehicleTask::getDuration)
                .sum();
    }
    
    /**
     * Result object containing the solution to the knapsack problem.
     */
    public static class KnapsackResult {
        private final List<VehicleTask> selectedTasks;
        private final int totalDuration;
        private final int totalImpact;
        private final int remainingCapacity;
        
        public KnapsackResult(
                List<VehicleTask> selectedTasks,
                int totalDuration,
                int totalImpact,
                int remainingCapacity
        ) {
            this.selectedTasks = selectedTasks;
            this.totalDuration = totalDuration;
            this.totalImpact = totalImpact;
            this.remainingCapacity = remainingCapacity;
        }
        
        public List<VehicleTask> getSelectedTasks() {
            return selectedTasks;
        }
        
        public int getTotalDuration() {
            return totalDuration;
        }
        
        public int getTotalImpact() {
            return totalImpact;
        }
        
        public int getRemainingCapacity() {
            return remainingCapacity;
        }
    }
}
