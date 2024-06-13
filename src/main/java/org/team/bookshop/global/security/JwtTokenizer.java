package org.team.bookshop.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.team.bookshop.domain.user.entity.Token;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.TokenRepository;
import org.team.bookshop.global.config.JwtConfig;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenizer {

  private final JwtConfig jwtConfig;
  private final PrincipalDetailsService principalDetailsService;
  private final TokenRepository tokenRepository;

  public String generateAccessToken(User user) {

    Claims claims = Jwts.claims();
    claims.put("userID", user.getId());
    claims.put("userEmail", user.getEmail());
    claims.put("role", user.getRole().getRoleName());

    long now = System.currentTimeMillis();
    return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(new Date())
          .setExpiration(new Date(now + JwtConfig.ACCESS_TOKEN_EXPIRATION_TIME))
          .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtConfig.getSecretKey().getBytes()))
          .compact();
  }

  public String generateRefreshToken(User user) {

    Claims claims = Jwts.claims();
    claims.put("userID", user.getId());
    claims.put("userEmail", user.getEmail());
    claims.put("role", user.getRole().getRoleName());

    long now = System.currentTimeMillis();
    return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(new Date())
          .setExpiration(new Date(now + JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME))
          .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtConfig.getSecretKey().getBytes()))
          .compact();
  }
  public String getUserId(String token) {
    return parseToken(token).get("userID").toString();
  }

  public Authentication getAuthentication(String token){
    Claims claims = parseToken(token);
    UserDetails userDetails = principalDetailsService.loadUserByUsername(claims.get("userID").toString());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public Claims parseToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtConfig.getSecretKey().getBytes())
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateAccessToken(String token) {
    try {
      parseToken(token);
      return true;
    } catch (ExpiredJwtException e) {
      log.error("Access token has expired: {}", e.getMessage());
    } catch (JwtException e) {
      log.error("Invalid access token: {}", e.getMessage());
    }
    return false;
  }

  public boolean validateRefreshToken(String token) {
    try {
      parseToken(token);  // parseToken에서 유효성 검사
      return !isTokenBlacklisted(token);
    } catch (ExpiredJwtException e) {
      log.error("Refresh token has expired: {}", e.getMessage());
    }
    return false;
  }


  public String updateAccessToken(String refreshToken) {
    if (validateRefreshToken(refreshToken)) {
      String userId = getUserId(refreshToken);
      UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
      User user = ((PrincipalDetails) userDetails).getUser();
      return generateAccessToken(user);
    }
    throw new ApiException("Invalid refresh token.", ErrorCode.INVALID_REFRESH_TOKEN);
  }

  public String updateRefreshToken(String refreshToken) {
    if (validateRefreshToken(refreshToken)) {
      String userId = getUserId(refreshToken);
      UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
      User user = ((PrincipalDetails) userDetails).getUser();
      return generateRefreshToken(user);
    }
    throw new ApiException("Invalid refresh token.", ErrorCode.INVALID_REFRESH_TOKEN);
  }

  public void addTokenToBlacklist(String refreshToken, String description){
    if(!isTokenBlacklisted(refreshToken)) {
      Token blacklistedToken = Token.builder()
          .token(refreshToken)
          .expires(LocalDateTime.now().plusSeconds(JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME / 1000))
          .description(description)
          .created(LocalDateTime.now())
          .build();
      tokenRepository.save(blacklistedToken);
      return;
    }
    throw new ApiException("Invalid refresh token.",ErrorCode.BANDED_TOKEN);
  }

  public String getJwtFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    return (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            ? authorizationHeader.substring(7)
            : null;
  }

  public boolean isTokenBlacklisted(String token) {
    return tokenRepository.findByToken(token).isPresent();
  }

  public String getRefreshTokenFromCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    return (cookies == null) ? null : Arrays.stream(cookies)
        .filter(cookie -> JwtConfig.REFRESH_JWT_COOKIE_NAME.equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }

  public Cookie setRefreshTokenToCookies(String token) {
    Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, token);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(token == null ? 0 : JwtConfig.JWT_COOKIE_MAX_AGE);
    return cookie;
  }
}
