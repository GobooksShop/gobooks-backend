package org.team.bookshop.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.entity.UserRole;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.config.JwtConfig;

@Component
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler,
    LogoutSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final String DEFAULT_REDIRECT_URL = "http://localhost:3000";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication)
        throws ServletException, IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        User user = findOrCreateUser(attributes);
        String jwtToken = jwtTokenizer.generateAccessToken(user);

        Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(JwtConfig.JWT_COOKIE_MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(DEFAULT_REDIRECT_URL);
        response.getWriter().write("Login successful");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication)
        throws IOException {
        Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(0); // Set the max age to 0 to delete the cookie
        cookie.setPath("/");
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(DEFAULT_REDIRECT_URL);
        response.getWriter().write("Logout successful");
    }

    private User findOrCreateUser(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) {
            throw new IllegalArgumentException("Response map is missing in the attributes");
        }

        String providerId = (String) response.get("id");
        String email = (String) response.get("email");
        String name = (String) response.get("name");
        String nickname = (String) response.get("nickname");

        return userRepository.findByProviderId(providerId).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProviderId(providerId);
            newUser.setNickname(nickname);
            newUser.setPassword("!gobooks123");
            newUser.setRole(UserRole.USER);
            return userRepository.save(newUser);
        });
    }
}
