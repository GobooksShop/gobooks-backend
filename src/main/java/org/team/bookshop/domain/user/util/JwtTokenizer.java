package org.team.bookshop.domain.user.util;

import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.global.config.JwtConfig;

public class JwtTokenizer {

    private final JwtConfig jwtConfig;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    @Autowired
    public JwtTokenizer(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateAccessToken(UserLoginDto userLoginDto) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(userLoginDto.getEmail())
            .claim("roles", Collections.singletonList(userLoginDto.getRole().name()))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey().getBytes())
            .compact();
    }

    public String generateRefreshToken(UserLoginDto userLoginDto) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(userLoginDto.getEmail())
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey().getBytes())
            .compact();
    }
}
