package edu.jhu.mrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.jhu.mrm.dto.RegisterAccountDTO;
import edu.jhu.mrm.model.Account;
import edu.jhu.mrm.model.Role;
import edu.jhu.mrm.repository.AccountRepository;

/**
 * File: AccountService.java
 * Service layer for Accounts
 * Author: Cory Drangel and Matthew Kim
 *
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account register(RegisterAccountDTO dto) {
        if (accountRepository.existsByUsername(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }

        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setEmail(dto.getEmail());
        account.setBuilding(dto.getBuilding());
        account.setApartmentNum(dto.getApartmentNum());
        account.setRole(Role.TENANT);

        return accountRepository.save(account);
    }

    public Account getByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found with username: " + username));
    }

    public Account getById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found with id: " + id));
    }

    public Account getByIdAsRequester(String id, String requesterUsername) {
        Account target = getById(id);
        Account requester = getByUsername(requesterUsername);
        assertSelfOrAdmin(target, requester);
        return target;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account update(String id, Account changes, String requesterUsername) {
        Account target = getById(id);
        Account requester = getByUsername(requesterUsername);

        assertSelfOrAdmin(target, requester);

        if (changes.getFirstName() != null)   target.setFirstName(changes.getFirstName());
        if (changes.getLastName() != null)    target.setLastName(changes.getLastName());
        if (changes.getEmail() != null)       target.setEmail(changes.getEmail());
        if (changes.getBuilding() != null)    target.setBuilding(changes.getBuilding());
        if (changes.getApartmentNum() != null) target.setApartmentNum(changes.getApartmentNum());

        if (changes.getPassword() != null && !changes.getPassword().isBlank()) {
            target.setPassword(passwordEncoder.encode(changes.getPassword()));
        }

        return accountRepository.save(target);
    }

    public void delete(String id) {
        if (!accountRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    private void assertSelfOrAdmin(Account target, Account requester) {
        boolean isAdmin = requester.getRole() == Role.ADMIN;
        boolean isSelf  = target.getId().equals(requester.getId());
        if (!isAdmin && !isSelf) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You can only modify your own account unless you are admin");
        }
    }
}