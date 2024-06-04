package org.team.bookshop.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;
import org.team.bookshop.global.error.exception.EntityNotFoundException;
import org.team.bookshop.global.security.JwtTokenizer;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(UserLoginDto userLoginDto) throws ApiException {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid credentials", ErrorCode.AUTHENTICATION_FAILURE);
        }

        return user;
    }

    public record TokenResponse(String accessToken, String refreshToken) {
    }
}
