package edu.jhu.mrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'TENANT')")
	public ResponseEntity<MaintRequest> createRequest(@RequestBody MaintRequest request) {
		return ResponseEntity.ok(requestService.create(request));
	}
	
	@GetMapping("/my")
	@PreAuthorize("hasRole('TENANT')")
	public ResponseEntity<List<MaintRequest>> getRequestsForTenant(@RequestParam String id) {
		return ResponseEntity.ok(requestService.getByTenantId(id));
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<MaintRequest>> getAll() {
		return ResponseEntity.ok(requestService.getAll());	
	}
	
	@GetMapping("/byAccountId/{id}")
	public ResponseEntity<List<MaintRequest>> getRequestByTenantId(@PathVariable String id) {
		return ResponseEntity.ok(requestService.getByAccountId(id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<MaintRequest>> getRequests(@PathVariable String id) {
		return ResponseEntity.ok(requestService.getById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MaintRequest> updateRequest(@PathVariable String id, @RequestBody MaintRequest update) {
		return ResponseEntity.ok(requestService.update(id, update));
	}
	
	@PutMapping("/assign/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MaintRequest> assignRequest(@PathVariable String id, @RequestBody MaintRequest request) {
		return ResponseEntity.ok(requestService.assign(id, request));
	}
}