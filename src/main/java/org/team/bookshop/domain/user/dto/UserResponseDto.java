package org.team.bookshop.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.entity.UserStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String email;
  private String nickname;
  private String name;
  private String phone;
  private Boolean termsAgreed;
  private Boolean marketingAgreed;
  private Boolean emailVerified;
  private UserStatus status;

  public static UserResponseDto fromEntity(User user) {
    return UserResponseDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .nickname(user.getNickname())
        .name(user.getName())
        .phone(user.getPhone())
        .termsAgreed(user.isTermsAgreed())
        .marketingAgreed(user.isMarketingAgreed())
        .emailVerified(user.isEmailVerified())
        .status(user.getStatus())
        .build();
  }
}
