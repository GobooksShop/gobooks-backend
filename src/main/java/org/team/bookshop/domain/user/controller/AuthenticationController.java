package org.team.bookshop.domain.user.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.user.dto.TokenResponseDto;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.service.AuthenticationService;
import org.team.bookshop.global.config.JwtConfig;
import org.team.bookshop.global.security.JwtTokenizer;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto,
        HttpServletResponse response) {
        User user = authenticationService.login(userLoginDto);

        if (user == null) {
            return new ResponseEntity(userLoginDto, HttpStatus.NOT_FOUND);
        }

        String jwtToken = jwtTokenizer.generateAccessToken(user);

        Cookie cookie = new Cookie(JwtConfig.JWT_COOKIE_NAME, jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(JwtConfig.JWT_COOKIE_MAX_AGE);

        response.addCookie(cookie);
        response.addHeader(HttpHeaders.AUTHORIZATION, user.getRole().getRole());

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/send-code")
    public void sendVerificationCode(@RequestParam String email) {
        authenticationService.sendVerificationCode(email);
    }

    @PostMapping("/verify-code")
    public boolean isVerifyCode(@RequestParam String email, @RequestParam String code) {
        return authenticationService.isVerifyCode(email, code);
    }
}


