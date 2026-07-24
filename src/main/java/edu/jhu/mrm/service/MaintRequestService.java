package edu.jhu.mrm.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.jhu.mrm.model.MaintRequest;
import edu.jhu.mrm.repository.MaintRequestRepository;

/**
 * File: MaintRequestService.java
 * Service layer for MaintRequest
 * Author: Cory Drangel and Matthew Kim
 * 
 */
@Service
public class MaintRequestService {
	
	@Autowired 
	private MaintRequestRepository requestRepository;
	
	public MaintRequest create(MaintRequest request) {
		MaintRequest newRequest = new MaintRequest();
		newRequest.setAccountId(request.getAccountId());
		newRequest.setCreatedDate(new Timestamp(System.currentTimeMillis()).toString());
		newRequest.setDescription(request.getDescription());
		newRequest.setId(request.getId());
		newRequest.setIssueType(request.getIssueType());
		newRequest.setLocation(request.getLocation());
		newRequest.setStatus(request.getStatus());
		newRequest.setUrgencyLevel(request.getUrgencyLevel());
		
		return requestRepository.save(newRequest);
	}
	
	public List<MaintRequest> getByAccountId(String id) {
		return requestRepository.findByAccountId(id);
	}
	
	public List<MaintRequest> getAll() {
		return requestRepository.findAll();
	}
	
	public MaintRequest getByRequestId(String id) {
		/*
		 * TODO: Update this so that it verifies a tenant
		 * can get their requests, and workers can only get requests
		 * that have been assigned to them.
		 */
		return requestRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Maintenance request not found with ID: " + id));
	}
	
	public MaintRequest update(String id, MaintRequest update) {
		/* 
		 * TODO: Update this so that it is not just a whole update.
		 * Should be dependent on role of user who submitted it and Tenant
		 * and Worker can only make certain updates. 
		 */
		MaintRequest newRequest = getByRequestId(id);
		
		if (update.getAccountId() != null) {
			newRequest.setAccountId(update.getAccountId());
		}
		
		if (update.getCreatedDate() != null) {
			newRequest.setCreatedDate(update.getCreatedDate());
		}
		
		if (update.getDescription() != null) {
			newRequest.setDescription(update.getDescription()); 
		}
		
		if (update.getIssueType() != null) {
			newRequest.setIssueType(update.getIssueType());
		}
		
		if (update.getLocation() != null) {
			newRequest.setLocation(update.getLocation());
		}
		
		if (update.getStatus() != null) {
			newRequest.setStatus(update.getStatus());
		}
		
		if (update.getUrgencyLevel() != null) {
			newRequest.setUrgencyLevel(update.getUrgencyLevel());
		}
		
		return newRequest;
	}
	
	public MaintRequest assign(String id, String workerId) {
		MaintRequest request = getByRequestId(id);
		
		request.setAssignedWorker(workerId);
		
		return request;
	}
}