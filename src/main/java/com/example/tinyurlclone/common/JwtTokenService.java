package com.example.tinyurlclone.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtTokenService{

    @Value("${application.security.jwt.secretKey}")
    private String secretKey;
    @Value("${application.security.jwt.issuer}")
    private String issuer;


    public String createToken(String subject, Date expiration, Map<String, ?> extraPayload) {
        JwtBuilder builder = Jwts.builder();
        if (extraPayload != null && extraPayload.size() > 0) {
            builder.setClaims(extraPayload);
        }
        return builder
                .setSubject(subject)
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .signWith(getSigninkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        try {
            Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        }catch (JwtException jwtException) {
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
