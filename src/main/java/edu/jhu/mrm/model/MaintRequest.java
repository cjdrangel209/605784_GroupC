package edu.jhu.mrm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * File: MaintRequest.java
 * This file contains the definition of the MaintRequest model
 * Specifically designed to be used with MongoDB
 * 
 * @author Cory Drangel and Matthew Kim
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
	
	@Field("assigned_worker")
	private String assignedWorker;

	private Location location;
	
	private String createdDate;
	
	private String comments;
	
	private String completedDate;
	
	public MaintRequest()
	{
	}

	/**
	 * @return {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 * 			{@link #id}
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return {@link #accountId}
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 * 			{@link #accountId}
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return {@link #issueType}
	 */
	public String getIssueType() {
		return issueType;
	}

	/**
	 * @param issueType
	 * 			{@link #issueType}
	 */
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	/**
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 * 			{@link #description}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return {@link #status}
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 * 			{@link #status}
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return {@link #urgencyLevel}
	 */
	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	/**
	 * @param urgencyLevel
	 * 			{@link #urgencyLevel}
	 */
	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}
	
	/**
	 * @return {@link #assignedWorker}
	 */
	public String getAssignedWorker() {
		return assignedWorker;
	}
	
	/**
	 * @param assignedWorker
	 * 			{@link #assignedWorker}
	 */
	public void setAssignedWorker(String assignedWorker) {
		this.assignedWorker = assignedWorker;
	}

	/**
	 * @return {@link #location}
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 * 			{@link #location}
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return {@link #createdDate}
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 * 			{@link #createdDate}
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * @return {@link #comments}
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * @param comments
	 * 			{@link #comments}
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * @return {@link #completedDate}
	 */
	public String getCompletedDate() {
		return completedDate;
	}

	/**
	 * @param completedDate
	 * 			{@link #completedDate}
	 */
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
}