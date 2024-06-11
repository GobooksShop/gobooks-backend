package org.team.bookshop.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.TokenRepository;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.domain.user.service.UserService;
import org.team.bookshop.global.config.JwtConfig;
import org.team.bookshop.global.error.exception.ApiException;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtTokenizer jwtTokenizer;
  private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwtAccessToken = jwtTokenizer.getJwtFromRequest(request);
      if (jwtAccessToken != null && jwtTokenizer.validateToken(jwtAccessToken,"access")) {
        handleValidToken(jwtAccessToken, request);
      } else {
        String refreshToken = getRefreshTokenFromCookies(request.getCookies());
        if (refreshToken != null && jwtTokenizer.validateToken(refreshToken, "refresh")) {
          String newAccessToken = jwtTokenizer.refreshAccessToken(refreshToken);
          response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
          handleValidToken(newAccessToken, request);
        }
      }
    } catch (ApiException ex) {
      log.error("Could not set user authentication in security context", ex);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized");
      return;
    }
    filterChain.doFilter(request, response);
  }

  private String getRefreshTokenFromCookies(Cookie[] cookies) {
    return (cookies == null) ? null : Arrays.stream(cookies)
            .filter(cookie -> JwtConfig.REFRESH_JWT_COOKIE_NAME.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);
  }

  private void handleValidToken(String token, HttpServletRequest request) {
    Long userId = Long.valueOf(jwtTokenizer.getUserId(token));
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              user, null, List.of(new SimpleGrantedAuthority(user.getRole().getRole())));
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
  }
}

