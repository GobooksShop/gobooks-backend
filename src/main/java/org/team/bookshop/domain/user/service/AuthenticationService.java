package org.team.bookshop.domain.user.service;


import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

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

    private final JavaMailSender emailSender;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, Long> codeExpiryTimes = new ConcurrentHashMap<>();
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 6;
    private static final long CODE_EXPIRY_DURATION = TimeUnit.MINUTES.toMillis(3); // 10분 유효기간

    @Async("taskExecutor")
    public void sendVerificationCode(String toEmail) {
        String code = generateVerificationCode(toEmail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + code + "\n이 코드는 3분 동안 유효합니다.");
        emailSender.send(message);
    }

    public boolean isVerifyCode(String email, String code) {
        if (isCodeExpired(email)) {
            verificationCodes.remove(email);
            codeExpiryTimes.remove(email);
            return false;
        }
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateVerificationCode(String email) {
        String code = generateNumericCode();
        verificationCodes.put(email, code);
        codeExpiryTimes.put(email, System.currentTimeMillis() + CODE_EXPIRY_DURATION);
        return code;
    }

    private String generateNumericCode() {
        StringBuilder builder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            builder.append(SECURE_RANDOM.nextInt(10)); // 0에서 9까지의 난수 생성
        }
        return builder.toString();
    }

    private boolean isCodeExpired(String email) {
        Long expiryTime = codeExpiryTimes.get(email);
        return expiryTime == null || System.currentTimeMillis() > expiryTime;
    }
}
