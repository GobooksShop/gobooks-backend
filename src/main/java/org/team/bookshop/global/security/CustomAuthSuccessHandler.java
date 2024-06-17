package org.team.bookshop.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.team.bookshop.domain.user.dto.TokenResponseDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.entity.UserRole;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.config.JwtConfig;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler,
    LogoutSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final String DEFAULT_REDIRECT_URL = "http://localhost:3000/login/oauth2/redirect";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        String provider = oauthToken.getAuthorizedClientRegistrationId(); // 제공자 ID 가져오기

        User user = findOrCreateUser(provider, attributes);

        String accessToken = jwtTokenizer.generateAccessToken(user);
        String refreshToken = jwtTokenizer.generateRefreshToken(user);

        response.addCookie(jwtTokenizer.setRefreshTokenToCookies(refreshToken));
        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
            .accessToken(accessToken)
            .userId(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole().getRoleName())
            .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String tokenResponseDtoJson = objectMapper.writeValueAsString(tokenResponseDto);
        String encodedTokenResponseDto = URLEncoder.encode(tokenResponseDtoJson,
            StandardCharsets.UTF_8.toString());

        Cookie tokenResponseDtoCookie = new Cookie("TokenResponseDto", encodedTokenResponseDto);
        tokenResponseDtoCookie.setPath("/");
        tokenResponseDtoCookie.setHttpOnly(false);
        tokenResponseDtoCookie.setMaxAge(60 * 5);
        response.addCookie(tokenResponseDtoCookie);

        response.sendRedirect(DEFAULT_REDIRECT_URL);
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.sendRedirect(DEFAULT_REDIRECT_URL);
    }

    private User findOrCreateUser(String provider, Map<String, Object> attributes) {
        final String providerId;
        final String email;
        final String name;
        final String nickname;

        switch (provider) {
            case "google":
                providerId = (String) attributes.get("sub");
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                nickname = name; // Google은 nickname 속성을 따로 제공하지 않음
                break;

            case "kakao":
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get(
                    "kakao_account");
                providerId = String.valueOf(attributes.get("id"));
                email = (String) kakaoAccount.get("email");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                name = (String) profile.get("nickname");
                nickname = name; // Kakao는 nickname을 profile 내에서 제공
                break;

            case "naver":
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                if (response == null) {
                    throw new IllegalArgumentException("Response map is missing in the attributes");
                }
                providerId = (String) response.get("id");
                email = (String) response.get("email");
                name = (String) response.get("name");
                nickname = (String) response.get("nickname");
                break;

            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }

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
