package org.team.bookshop.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.global.config.JwtConfig;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {

  private final JwtConfig jwtConfig;
  private final PrincipalDetailsService principalDetailsService;

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
    UserDetails userDetails = principalDetailsService.loadUserByUsername(claims.get("userEmail").toString());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean isExpired(String token) {
    Date expiredDate = parseToken(token).getExpiration();
    return expiredDate.before(new Date());
  }

  public Claims parseToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtConfig.getSecretKey().getBytes())
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(jwtConfig.getSecretKey().getBytes())
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // 리프레시 토큰을 이용해 토큰을 재발급 하기위한 함수
  public String refreshAccessToken(String refreshToken) {
    if (isExpired(refreshToken)) {
      throw new IllegalArgumentException("Refresh token is expired");
    }
    String userId = getUserId(refreshToken);
    UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
    User user = ((PrincipalDetails) userDetails).getUser();
    return generateAccessToken(user);
  }

  public String refreshRefreshToken(String refreshToken) {
    if (isExpired(refreshToken)) {
      String userId = getUserId(refreshToken);
      UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
      User user = ((PrincipalDetails) userDetails).getUser();
      return generateRefreshToken(user);
    }
    return refreshToken;
  }

}
