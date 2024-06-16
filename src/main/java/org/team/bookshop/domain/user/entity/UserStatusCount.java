package org.team.bookshop.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserStatusCount {
  private UserStatus status;
  private long count;

}
