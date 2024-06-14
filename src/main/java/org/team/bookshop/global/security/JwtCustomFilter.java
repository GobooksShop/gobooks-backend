package org.team.bookshop.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Slf4j
@RequiredArgsConstructor
public class JwtCustomFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtTokenizer jwtTokenizer;

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
    @NotNull FilterChain filterChain) throws ServletException, IOException {

      String jwtAccessToken = jwtTokenizer.getJwtFromRequest(request);

      if (jwtAccessToken != null && jwtTokenizer.validateAccessToken(jwtAccessToken)) {
        setSecurityContext(jwtAccessToken, request);
      } else {
        String refreshToken = jwtTokenizer.getRefreshTokenFromCookies(request);
        if (refreshToken != null && jwtTokenizer.validateRefreshToken(refreshToken)) {
          String newAccessToken = jwtTokenizer.updateAccessToken(refreshToken);
          response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
          setSecurityContext(newAccessToken, request);
          responseUnauthorized(response, "Token refreshed, please retry with new access token");
          return;
//          throw new ApiException("Token refreshed, please retry with new access token", ErrorCode.EXPIRED_ACCESS_TOKEN);

        }
      }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    String method = request.getMethod();
    return method.equals("OPTIONS") || //preflight 요청을 처리하기위해 사용
           path.startsWith("/api"); // 프론트 accsstoken 인증 문제 해결 전까지는 모든 요청 스킵함.
//           path.startsWith("/api/auth") ||
//           path.startsWith("/api/users") ||
//           path.startsWith("/api/categories") && method.equals("GET") ||
//           path.startsWith("/api/products") && method.equals("GET");
  }

  private void responseUnauthorized(HttpServletResponse response, String message) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("message", message);

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponse = objectMapper.writeValueAsString(responseBody);

    response.getWriter().write(jsonResponse);
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

