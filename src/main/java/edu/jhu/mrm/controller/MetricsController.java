package edu.jhu.mrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.jhu.mrm.service.MetricsService;


/**
 * File: MetricsController.java
 * This file contains the controller endpoints for the Metrics available to admins
 * 
 * @author Cory Drangel and Mathew Kim
 */
@RestController
@RequestMapping("/mrm/admin")
public class MetricsController {
	
	@Autowired
	private MetricsService metricsService;
	
	/**
	 * REST endpoint for getting the metrics for the requests. Current metrics are
	 * the amount of tickets submitted in the last week and the average time of
	 * completion for a ticket that is marked as "Complete"
	 * Admins only.
	 * 
	 * @return The response entity with the string displaying the metrics.
	 */
	@GetMapping("/metrics")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> getMetrics() {
		String ticketsSubmittedPerWeek = "Tickets submitted in the last week: " +
				metricsService.getRequestsInLastWeek();
		
		String avgCompletionTime = "Average completion time: " +
				metricsService.getAvgCompleteTime();
		
		return ResponseEntity.ok(ticketsSubmittedPerWeek + "\n" + avgCompletionTime);
	}
}