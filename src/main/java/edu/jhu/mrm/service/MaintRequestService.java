package edu.jhu.mrm.service;

import java.sql.Timestamp;
import java.util.List;

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
 * 
 * @author Cory Drangel and Matthew Kim
 */
@Service
public class MaintRequestService {
	
	@Autowired 
	private MaintRequestRepository requestRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * Utility function to get the current user's account
	 * 
	 * @param username
	 * 			Username to get the Account of
	 * @return An account
	 */
	private Account getByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found with username: " + username));
    }
	
	/**
	 * Creates a new MaintRequest
	 * 
	 * @param request
	 * 			The information to use for the new maintenance request object
	 * @return The new MaintRequest object
	 */
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
	
	/**
	 * Finds all MaintRequest objects associated with an account ID
	 * 
	 * @param id
	 * 			The accountId to search by
	 * @return List of MaintRequest objects
	 */
	public List<MaintRequest> getByAccountId(String id) {
		return requestRepository.findByAccountId(id);
	}
	
	/**
	 * Finds all MaintRequest objects currently in database
	 * 
	 * @return List of MaintRequest objects
	 */
	public List<MaintRequest> getAll() {
		return requestRepository.findAll();
	}
	
	/**
	 * Gets a single MaintRequest object. Tenants can only search for their own requests.
	 * Workers can only search for requests assigned to them.
	 * 
	 * @param requestId
	 * 			The ID of the MaintRequest to search for
	 * @param requestorUsername
	 * 			The user name of the person searching for the maintenance request
	 * @return A MaintRequest object
	 */
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
	
	/**
	 * Updates a MaintRequest object.
	 * 
	 * @param id
	 * 			The ID for the MaintRequest to update
	 * @param update
	 * 			The updated information to utilize
	 * @param requestorUsername
	 * 			The user name of the person updating the maintenance request
	 * @return A MaintRequest object that has been updated
	 */
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
	
	/**
	 * Assigns a worker to a MaintRequest object
	 * 
	 * @param id
	 * 			The ID of the MaintRequest object
	 * @param workerId
	 * 			The ID for the associated worker
	 * @param requestorUsername
	 * 			The user name for the person assigning the worker to the MaintRequest 
	 * @return A MaintRequest object with an assigned worker
	 */
	public MaintRequest assign(String id, String workerId, String requestorUsername) {
		MaintRequest request = getByRequestId(id, requestorUsername);
		
		request.setAssignedWorker(workerId);
		
		return request;
	}
}