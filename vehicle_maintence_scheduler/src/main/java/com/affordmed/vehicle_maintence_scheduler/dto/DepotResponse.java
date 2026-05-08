package com.affordmed.vehicle_maintence_scheduler.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response wrapper for depot list from external API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepotResponse {
    private List<Depot> depots;
}
