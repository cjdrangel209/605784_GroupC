package edu.jhu.mrm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.jhu.mrm.model.MaintRequest;

/**
 * File: MaintRequestRepository.java
 * MongoRespoitory for MaintRequest with CRUD and username lookups
 * Author: Cory Drangel and Matthew Kim
 */
@Repository
public interface MaintRequestRepository extends MongoRepository<MaintRequest, String>{
	
}