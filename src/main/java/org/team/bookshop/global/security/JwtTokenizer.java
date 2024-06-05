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
  public String getUserEmail(String token) {
    return parseToken(token).get("userEmail").toString();
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
}
