package org.team.bookshop.domain.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.global.config.JwtConfig;

@Component
public class JwtTokenizer {

    private final JwtConfig jwtConfig;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    @Autowired
    public JwtTokenizer(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateAccessToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("roles", user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getRoleName().name())
                .collect(Collectors.toList()))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey().getBytes())
            .compact();
    }

    public String generateRefreshToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey().getBytes())
            .compact();
    }
//d public Claims parseToken(String token) {
//        return Jwts.parserBuilder()
//            .setSigningKey(getSigningKey())
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//    }
//d
// d   public boolean validateToken(String token) {
//        try {
//            parseToken(token);
//            return true;
//        } catch (Exception e) {
//            // Log the exception
//            return false;
//        }
//    }
}
