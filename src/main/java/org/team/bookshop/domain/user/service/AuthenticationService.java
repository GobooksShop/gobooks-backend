package org.team.bookshop.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.domain.user.util.JwtTokenizer;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(UserLoginDto userLoginDto) throws Exception {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
            .orElseThrow(() -> new Exception("User not found"));
<<<<<<< HEAD

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        String accessToken = jwtTokenizer.generateAccessToken(user);
        String refreshToken = jwtTokenizer.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken);
    }

=======

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        String accessToken = jwtTokenizer.generateAccessToken(user);
        String refreshToken = jwtTokenizer.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Getter
>>>>>>> 12b7e2e (feat: jwt기능 추가중 테스트 필요)
    public record TokenResponse(String accessToken, String refreshToken) {

    }
}
