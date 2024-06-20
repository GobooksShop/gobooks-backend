package org.team.bookshop.global.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.global.config.WebConfig;

@Component
@RequiredArgsConstructor
public class CustomCorsFilter extends HttpFilter {

  private final WebConfig webConfig;

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    response.setHeader("Access-Control-Allow-Origin", webConfig.getBaseUrl());
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Expose-Headers", "Authorization");
    chain.doFilter(request, response);
  }
}
