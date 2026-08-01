package edu.jhu.mrm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.jhu.mrm.model.MaintRequest;
import edu.jhu.mrm.service.MaintRequestService;

/**
 * File: MaintRequestController.java
 * This file contains the controller containing endpoints for Maintenance
 * Requests which includes create a request, get request by ID, update request
 * Author: Cory Drangel and Matthew Kim
 * 
 */

@RestController
@RequestMapping("/mrm/requests")
public class MaintRequestController {
	
	@Autowired
	private MaintRequestService requestService;
	
	/**
	 * Utility function to obtain the user's name
	 * 
	 * @return The user name of the current user
	 */
	private String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
	
	/**
	 * REST endpoint for creating a new maintenance request in the system.
	 * Tenants and Admins only.
	 * 
	 * @param request
	 * 			A new maintenance request
	 * @return The response entity with the newly created request
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'TENANT')")
	public ResponseEntity<MaintRequest> createRequest(@RequestBody MaintRequest request) {
		return ResponseEntity.ok(requestService.create(request));
	}
	
	/**
	 * REST endpoint for showing a list of all requests created by a tenant.
	 * 
	 * @param id
	 * 			The id of the tenant
	 * @return The response entity with the list of maintenance requests.
	 */
	@GetMapping("/my")
	@PreAuthorize("hasRole('TENANT')")
	public ResponseEntity<List<MaintRequest>> getRequestsForTenant(@RequestParam String id) {
		return ResponseEntity.ok(requestService.getByAccountId(id));
	}
	
	/**
	 * REST endpoint for returning information about all requests in the system.
	 * Admin only.
	 * 
	 * @return The response entity with the list of maintenance requests.
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<MaintRequest>> getAll() {
		return ResponseEntity.ok(requestService.getAll());	
	}
	
	/**
	 * REST endpoint for getting information about all requests submitted by a tenant
	 * Admin only.
	 * 
	 * @param id
	 * 			The id of the tenant to search by.
	 * @return The response entity with the list of maintenance requests.
	 */
	@GetMapping("/byAccountId/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<MaintRequest>> getRequestByAccounttId(@PathVariable String id) {
		return ResponseEntity.ok(requestService.getByAccountId(id));
	}
	
	/**
	 * REST endpoint for getting information about a single request.
	 * 
	 * @param id
	 * 			The id of the request to search for.
	 * @return The response entity with the maintenance request.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<MaintRequest> getRequests(@PathVariable String id) {
		return ResponseEntity.ok(requestService.getByRequestId(id, currentUsername()));
	}
	
	/**
	 * REST endpoint for updating the status of the maintenance request.
	 * 
	 * @param id
	 * 			The id of the maintenance request
	 * @param update
	 * 			The new information to update the maintenance request with
	 * @return The entity response with the updated maintenance request
	 */
	@PutMapping("/{id}")
	public ResponseEntity<MaintRequest> updateRequest(@PathVariable String id, @RequestBody MaintRequest update) {
		return ResponseEntity.ok(requestService.update(id, update, currentUsername()));
	}
	
	/**
	 * REST endpoint for assigning a maintenance request to a worker.
	 * Admin only.
	 * 
	 * @param id
	 * 			The id of the maintenance request.
	 * @param workerId
	 * 			The id of the worker to assign the request to.
	 * @return The response entity with the updated maintenance request.
	 */
	@PutMapping("/assign/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MaintRequest> assignRequest(@PathVariable String id, @RequestParam String workerId) {
		return ResponseEntity.ok(requestService.assign(id, workerId, currentUsername()));
	}
}