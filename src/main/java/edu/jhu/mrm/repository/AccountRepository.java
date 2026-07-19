package edu.jhu.mrm.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.jhu.mrm.model.Account;

/**
 * File: AccountRepository.java
 * MongoRepository for Accounts with CRUD and username lookups
 * Author: Cory Drangel and Matthew Kim
 *
 */

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
}
