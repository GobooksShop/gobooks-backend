package org.team.bookshop.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchDto {
  private final UserSearchType searchType;
  private final String keyword;
}
