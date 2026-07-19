package edu.jhu.mrm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.jhu.mrm.dto.RegisterAccountDTO;
import edu.jhu.mrm.model.Account;
import edu.jhu.mrm.security.JWTUtil;
import edu.jhu.mrm.service.AccountService;

/**
 * File: AuthController.java
 * Controller file for handling login and registration with tokens
 * Author: Cory Drangel and Matthew Kim
 */
@RestController
@RequestMapping("/mrm/auth")
public class AuthController {

    @Autowired private AccountService accountService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody RegisterAccountDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username,
                                                     @RequestParam String password) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid login credentials"));
        }

        Account account = accountService.getByUsername(username);
        String token = jwtUtil.generateToken(account.getUsername(), account.getRole().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", account.getUsername(),
                "role", account.getRole().name()
        ));
    }
}
