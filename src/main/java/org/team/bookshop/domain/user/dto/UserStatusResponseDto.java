package org.team.bookshop.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserStatusResponseDto {
  private long total;
  private long active;
  private long suspended;
  private long banned;
  private long dormant;
  private long canceled;
}
