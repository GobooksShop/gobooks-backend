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
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.service.UserService;
import org.team.bookshop.global.config.JwtConfig;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final JwtTokenizer jwtTokenizer;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if(authorizationHeader == null ||
        request.getCookies() == null
    ) {
      filterChain.doFilter(request, response);
      return;
    }

    Cookie jwtTokenCookie = Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals(JwtConfig.REFRESH_JWT_COOKIE_NAME))
        .findFirst()
        .orElse(null);

    if(jwtTokenCookie == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwtToken = jwtTokenCookie.getValue();

    if(jwtTokenizer.isExpired(jwtToken)){
      filterChain.doFilter(request, response);
      return;
    }

    String email = jwtTokenizer.getUserEmail(jwtToken);
    User user = userService.getUserByEmail(email);

    if(user == null){
      filterChain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        user, null, List.of(new SimpleGrantedAuthority(user.getRole().getRole())));

    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }
}

