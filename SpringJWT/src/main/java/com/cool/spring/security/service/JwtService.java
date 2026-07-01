package com.cool.spring.security.service;

import com.cool.spring.security.dao.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateAccessToken(UserAccount userAccount) {
        Map<String, Object> claims = new HashMap<>();
            claims.put("id", userAccount.getId());
            claims.put("username", userAccount.getUsername());
            claims.put("tokenType", "ACCESS");

        return generateToken(claims, userAccount, accessExpiration);
    }

    public String generateRefreshToken(UserAccount userAccount) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userAccount.getId());
        claims.put("username", userAccount.getUsername());
        claims.put("tokenType", "REFRESH");

        return generateToken(claims, userAccount, refreshExpiration);
    }

    private String generateToken(Map<String, Object> extraClaims, UserAccount userAccount, long expiration) {
        return Jwts.builder().claims(extraClaims).subject(userAccount.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey()).compact();
    }

    public boolean isTokenValid(String token, String expectedType) {
        try {

            Claims claims = extractAllClaims(token);
            String type = claims.get("tokenType", String.class);

            return expectedType.equals(type) && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
