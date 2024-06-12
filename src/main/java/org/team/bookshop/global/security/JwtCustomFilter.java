package org.team.bookshop.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.exception.ApiException;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtTokenizer jwtTokenizer;

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
    @NotNull FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwtAccessToken = jwtTokenizer.getJwtFromRequest(request);
      if (jwtAccessToken != null && jwtTokenizer.validateAccessToken(jwtAccessToken)) {
        setSecurityContext(jwtAccessToken, request);
      } else {
        String refreshToken = jwtTokenizer.getRefreshTokenFromCookies(request);
        if (refreshToken != null && jwtTokenizer.validateRefreshToken(refreshToken)) {
          String newAccessToken = jwtTokenizer.updateAccessToken(refreshToken);
          response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
          setSecurityContext(newAccessToken, request);
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

  private void setSecurityContext(String token, HttpServletRequest request) {
    Long userId = Long.valueOf(jwtTokenizer.getUserId(token));
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              user, null, List.of(new SimpleGrantedAuthority(user.getRole().getRoleName())));
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
  }
}

