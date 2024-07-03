package com.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenSevice {

    @Value("60")
    private Long EXPIRATION;

    @Value("${security.jwt.secret}")
    private String SECRET;

    public SecretKey getSecretKey() {
        byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {

        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + EXPIRATION *60 * 1000);

        return Jwts.builder()
                .header()
                    .type("JWT")
                    .and()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .claims(claims)
                .signWith(getSecretKey(),Jwts.SIG.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateToken(String token) {
        try {
            getUsernameFromToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

}