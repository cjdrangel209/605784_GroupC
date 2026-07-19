package edu.jhu.mrm.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * File: JWTUtil.java
 * Utility that creates and verifies JWTs
 * Functions for generating a token based on username and for validating
 * Author: Cory Drangel and Matthew Kim
 */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    public String generateToken(String username, String role) {
        return JWT.create()
                .withSubject("MRM User")
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .withIssuer("mrm")
                .sign(Algorithm.HMAC256(secret));
    }

    /** Verifies signature and expiry, returning the username claim. */
    public String validateTokenAndGetUsername(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("MRM User")
                .withIssuer("mrm")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
