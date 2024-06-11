package org.team.bookshop.domain.user.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.user.dto.TokenResponseDto;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.entity.Token;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.TokenRepository;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.domain.user.service.AuthenticationService;
import org.team.bookshop.global.config.JwtConfig;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;
import org.team.bookshop.global.error.exception.EntityNotFoundException;
import org.team.bookshop.global.security.JwtTokenizer;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto,
        HttpServletResponse response) {
        User user = authenticationService.login(userLoginDto);

        if (user == null) {
            return new ResponseEntity(userLoginDto, HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtTokenizer.generateAccessToken(user);
        String refreshToken = jwtTokenizer.generateRefreshToken(user);

        Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // https 적용시 사용
        cookie.setPath("/");
        cookie.setMaxAge(JwtConfig.JWT_COOKIE_MAX_AGE);

        response.addCookie(cookie);
        TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, user.getName(), user.getEmail(), user.getRole().getRole());

        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout request received");
        Cookie jwtTokenCookie = Arrays.stream(request.getCookies())
            .filter(cookie -> JwtConfig.REFRESH_JWT_COOKIE_NAME.equals(cookie.getName()))
            .findFirst()
            .orElse(null);

        if (jwtTokenCookie != null) {
            String refreshToken = jwtTokenCookie.getValue();
            Token blacklistedToken = new Token();
            blacklistedToken.setToken(refreshToken);
            blacklistedToken.setType("refresh");
            blacklistedToken.setExpires(
                LocalDateTime.now().plusSeconds(JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME / 1000));
            blacklistedToken.setCreated(LocalDateTime.now());
            tokenRepository.save(blacklistedToken);
        }

        Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // https 적용시 사용
        cookie.setPath("/"); // 쿠키의 경로를 지정해야 브라우저가 올바르게 삭제합니다.
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = Arrays.stream(request.getCookies())
            .filter(cookie -> JwtConfig.REFRESH_JWT_COOKIE_NAME.equals(cookie.getName()))
            .findFirst()
            .orElse(null);

        if (refreshTokenCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String refreshToken = refreshTokenCookie.getValue();
        if (tokenRepository.findByToken(refreshToken).isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken;
        String newRefreshToken = refreshToken;

        try {
            newAccessToken = jwtTokenizer.refreshAccessToken(refreshToken);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (jwtTokenizer.isExpired(refreshToken)) {
            newRefreshToken = jwtTokenizer.refreshRefreshToken(refreshToken);
            Cookie newCookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, newRefreshToken);
            newCookie.setHttpOnly(true);
            newCookie.setPath("/");
            newCookie.setMaxAge(JwtConfig.JWT_COOKIE_MAX_AGE);
            response.addCookie(newCookie);
        }

        Long userId = Long.valueOf(jwtTokenizer.getUserId(newAccessToken));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        TokenResponseDto tokenResponseDto = new TokenResponseDto(newAccessToken, user.getName(), user.getEmail(), user.getRole().getRole());

        return ResponseEntity.ok(tokenResponseDto);
    }


    @PostMapping("/send-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        authenticationService.sendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        boolean isVerified = authenticationService.isVerifyCode(email, code);
        return ResponseEntity.ok(isVerified);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}


