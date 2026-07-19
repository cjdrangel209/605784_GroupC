package edu.jhu.mrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.jhu.mrm.model.Account;
import edu.jhu.mrm.service.AccountService;

/**
 * File: AccountController.java
 * This file contains the controller containing endpoints for Account
 * which includes getting accounts, updating and deleting
 * Author: Cory Drangel and Matthew Kim
 *
 */

@RestController
@RequestMapping("/mrm/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getByIdAsRequester(id, currentUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable String id,
                                          @RequestBody Account changes) {
        return ResponseEntity.ok(accountService.update(id, changes, currentUsername()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id) {
        accountService.delete(id);
        return ResponseEntity.ok("Successfully deleted account: " + id);
    }
    
}
