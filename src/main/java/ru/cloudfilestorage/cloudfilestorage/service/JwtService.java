package ru.cloudfilestorage.cloudfilestorage.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.cloudfilestorage.cloudfilestorage.domain.entity.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    private static final Integer ONE_YEAR = 100000 * 60 * 24;

    private final Key key;

    public JwtService(@Value("${token.signing.key}") String key) {
        log.info("JWT key {}", key);
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String extractEmail(String token) {
        return extractAllClaims(token)
                .getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
        }
        return generateToken(claims, userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ONE_YEAR))
                .signWith(key)
                .compact();
    }

    public boolean isValid(@NotNull String token, @NotNull String username) {
        final Claims claims = extractAllClaims(token);
        final String login = claims.getSubject();
        final Date expiration = claims.getExpiration();

        return username.equals(login) && !expiration.before(new Date(System.currentTimeMillis()));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
