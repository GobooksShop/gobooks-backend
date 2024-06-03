package org.team.bookshop.domain.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD
import org.team.bookshop.domain.user.dto.TokenResponseDto;
import org.team.bookshop.domain.user.dto.UserLoginDto;
=======
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.dto.UserLoginResponseDto;
>>>>>>> 12b7e2e (feat: jwt기능 추가중 테스트 필요)
import org.team.bookshop.domain.user.service.AuthenticationService;


@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
<<<<<<< HEAD
    public ResponseEntity<TokenResponseDto> login(
=======
    public ResponseEntity<UserLoginResponseDto> login(
>>>>>>> 12b7e2e (feat: jwt기능 추가중 테스트 필요)
        @Valid @RequestBody UserLoginDto userLoginDto) {
        try {
            AuthenticationService.TokenResponse tokenResponse = authenticationService.login(
                userLoginDto);
<<<<<<< HEAD
            TokenResponseDto response = new TokenResponseDto(
=======
            UserLoginResponseDto response = new UserLoginResponseDto(
>>>>>>> 12b7e2e (feat: jwt기능 추가중 테스트 필요)
                tokenResponse.accessToken(),
                tokenResponse.refreshToken()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
