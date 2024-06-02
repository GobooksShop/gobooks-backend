package org.team.bookshop.domain.user.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.util.JwtTokenizer;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtTokenizer jwtTokenizer;

    public AuthenticationService(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    public TokenResponse login(UserLoginDto userLoginDto) throws Exception {
        if (isAuthenticated(userLoginDto)) {
            String accessToken = jwtTokenizer.generateAccessToken(userLoginDto);
            String refreshToken = jwtTokenizer.generateRefreshToken(userLoginDto);
            return new TokenResponse(accessToken, refreshToken);
        } else {
            throw new Exception("Authentication failed");
        }
    }

    private boolean isAuthenticated(UserLoginDto userLoginDto) {
        return true;
    }

    @Getter
    public record TokenResponse(String accessToken, String refreshToken) {

    }
}
