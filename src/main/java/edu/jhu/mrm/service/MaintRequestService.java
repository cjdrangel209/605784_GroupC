package edu.jhu.mrm.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<MaintRequest> getByTenantId(String id) {
		//TODO
		return null;
	}
	
	public List<MaintRequest> getAll() {
		return requestRepository.findAll();
	}
	
	public List<MaintRequest> getRequestByTenantId(String id) {
		//TODO
		return null;
	}
	
	public List<MaintRequest> getByAccountId(String id) {
		//TODO
		return null;
	}
	
	public List<MaintRequest> getById(String id) {
		//TODO
		return null;
	}
	
	public MaintRequest update(String id, MaintRequest udpate) {
		//TODO
		return null;
	}
	
	public MaintRequest assign(String id, MaintRequest request) {
		//TODO
		return null;
	}
}