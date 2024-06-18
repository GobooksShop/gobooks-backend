package org.team.bookshop.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponseDto {

    private String accessToken;
    private Long userId;
    private String name;
    private String email;
    private String role;
}
