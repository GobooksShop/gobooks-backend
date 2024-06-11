package org.team.bookshop.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
public class JwtTokenizer {

  private final JwtConfig jwtConfig;
  private final PrincipalDetailsService principalDetailsService;
  private final TokenRepository tokenRepository;

  public String generateAccessToken(User user) {

    Claims claims = Jwts.claims();
    claims.put("userID", user.getId());
    claims.put("userEmail", user.getEmail());
    claims.put("role", user.getRole().getRole());

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
    claims.put("role", user.getRole().getRole());

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

  public boolean validateToken(String token, String typeName) {
    try {
      Jwts.parser()
          .setSigningKey(jwtConfig.getSecretKey().getBytes())
          .parseClaimsJws(token);
      return !isTokenBlacklisted(token);
    } catch (ExpiredJwtException e) {
      addTokenToBlacklist(token, typeName);
      return false;
    }
    catch (Exception e) {
      return false;
    }
  }

  // 리프레시 토큰을 이용해 토큰을 재발급 하기위한 함수
  public String refreshAccessToken(String refreshToken) {
    if (!validateToken(refreshToken, "refresh")) {
      throw new ApiException("Refresh token is expired", ErrorCode.REFRESH_TOKEN_EXPIRED);
    }
    String userId = getUserId(refreshToken);
    UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
    User user = ((PrincipalDetails) userDetails).getUser();
    return generateAccessToken(user);
  }

  public String refreshRefreshToken(String refreshToken) {
      addTokenToBlacklist(refreshToken, "refresh");
      String userId = getUserId(refreshToken);
      UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
      User user = ((PrincipalDetails) userDetails).getUser();
      return generateRefreshToken(user);
  }

  public void addTokenToBlacklist(String token, String typeName){
    Token blacklistedToken = Token.builder()
            .token(token)
            .type(typeName)
            .expires(LocalDateTime.now().plusSeconds(JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME / 1000))
            .created(LocalDateTime.now())
            .build();
    tokenRepository.save(blacklistedToken);
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

}
