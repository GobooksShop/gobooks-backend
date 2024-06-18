package org.team.bookshop.domain.user.controller;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.domain.user.service.AuthenticationService;
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

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto,
                                                  HttpServletResponse response) {
        User user = authenticationService.login(userLoginDto);

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
        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout request received");
        String refreshToken = jwtTokenizer.getRefreshTokenFromCookies(request);

        if (refreshToken != null) {
            jwtTokenizer.addTokenToBlacklist(refreshToken, "request user logout");
        }
        response.addCookie(jwtTokenizer.setRefreshTokenToCookies(null));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenizer.getRefreshTokenFromCookies(request);

        try {
            if (refreshToken != null && jwtTokenizer.validateRefreshToken(refreshToken)) {
                String newAccessToken = jwtTokenizer.updateAccessToken(refreshToken);
                String newRefreshToken = jwtTokenizer.updateRefreshToken(refreshToken);
                jwtTokenizer.addTokenToBlacklist(refreshToken, "refresh token rotation");
                response.addCookie(jwtTokenizer.setRefreshTokenToCookies(newRefreshToken));

                Long userId = Long.valueOf(jwtTokenizer.getUserId(newAccessToken));
                User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

                TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                    .accessToken(newAccessToken)
                    .userId(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole().getRoleName())
                    .build();
                return ResponseEntity.ok(tokenResponseDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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


