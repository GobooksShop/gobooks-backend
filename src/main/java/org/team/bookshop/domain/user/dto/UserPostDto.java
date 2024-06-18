package org.team.bookshop.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.entity.UserRole;
import org.team.bookshop.domain.user.entity.UserStatus;

@Getter
@Setter
public class UserPostDto {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 빈 값이 들어올 수 없습니다.")
    private String email;

    @NotBlank(message = "패스워드는 빈 값이 들어올 수 없습니다.")
    @Size(min = 8, max = 32, message = "패스워드는 최소 8자리 이상, 최대 32자리까지 가능합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,32}$", message = "패스워드는 영문, 숫자, 특수문자(!@#$%^&*) 조합으로 입력해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 빈 값이 들어올 수 없습니다.")
    private String nickname;

    @NotBlank(message = "이름은 빈 값이 들어올 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,}$", message = "이름은 2자리 이상 한글 또는 영문만 입력 가능합니다.")
    private String name;

    @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 10자리 또는 11자리 숫자여야 합니다.")
    private String phone;

    private Boolean termsAgreed; // 이용 약관 동의
    private Boolean marketingAgreed; // 선택 약관 동의
    private Boolean emailVerified;  // 이메일 인증 여부

    // 기본 생성자에서 기본값 설정
    public UserPostDto() {
        this.termsAgreed = false;
        this.marketingAgreed = false;
        this.emailVerified = false;
    }

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setTermsAgreed(this.termsAgreed);
        user.setMarketingAgreed(this.marketingAgreed);
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setEmailVerified(true);
        return user;
    }

    public UserPostDto toDto(User user) {
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setNickname(user.getNickname());
        setName(user.getName());
        setPhone(user.getPhone());
        setMarketingAgreed(user.isMarketingAgreed());
        return this;
    }
}
