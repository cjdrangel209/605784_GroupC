package edu.jhu.mrm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * File: MaintRequest.java
 * This file contains the definition of the MaintRequest model
 * Specifically designed to be used with MongoDB
 * Author: Cory Drangel and Matthew Kim
 * 
 */
@Document(collection = "request")
public class MaintRequest {
	@Id
	@Indexed(unique = true)
	@Field("request_id")
	private String id;
	
	@Field("account_id")
	private String accountId;
	
	private String issueType;
	
	private String description;
	
	private String status;
	
	private String urgencyLevel;

	private Location location;
	
	private String createdDate;
	
	public MaintRequest()
	{
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}