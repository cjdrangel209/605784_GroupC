package edu.jhu.mrm.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.jhu.mrm.model.Account;
import edu.jhu.mrm.model.MaintRequest;
import edu.jhu.mrm.model.Role;
import edu.jhu.mrm.repository.AccountRepository;
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
	
	@Autowired
	private AccountRepository accountRepository;
	
	private Account getByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found with username: " + username));
    }
	
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
	
	public MaintRequest getByRequestId(String requestId, String requestorUsername) {
		
		Account requestor = getByUsername(requestorUsername);
		
		MaintRequest request = requestRepository.findById(requestId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Maintenance request not found with ID: " + requestId));
		
		// Tenants can only get their own requests
		if (requestor.getRole() == Role.TENANT &&
				requestor.getId() != request.getAccountId()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This maintenance request is not associated with this tenant.");
		}
		// Workers can only get requests assigned to them
		else if (requestor.getRole() == Role.WORKER &&
				requestor.getId() != request.getAssignedWorker()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This maintenance request is not associated with this worker.");
		}
		
		return request;
	}
	
	public MaintRequest update(String id, MaintRequest update, String requestorUsername) {
		
		MaintRequest newRequest = getByRequestId(id, requestorUsername);
		
		Account requestor = getByUsername(requestorUsername);
		
		// Tenant can only update comments
		if (requestor.getRole() == Role.TENANT) {
			if (update.getComments() != null) {
				newRequest.setComments(update.getComments());
			}
		}
		// Worker can only update comments or status
		else if (requestor.getRole() == Role.WORKER) {
			if (update.getComments() != null) {
				newRequest.setComments(update.getComments());
			}
			
			if (update.getStatus() != null) {
				newRequest.setStatus(update.getStatus());
			}
		}
		// Admin can update any field
		else if (requestor.getRole() == Role.ADMIN) {
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
			
			if (update.getComments() != null) {
				newRequest.setComments(update.getComments());
			}
		}
		
		return newRequest;
	}
	
	public MaintRequest assign(String id, String workerId, String requestorUsername) {
		MaintRequest request = getByRequestId(id, requestorUsername);
		
		request.setAssignedWorker(workerId);
		
		return request;
	}
}