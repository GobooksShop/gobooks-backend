package org.team.bookshop.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserUpdateDto {

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
}
