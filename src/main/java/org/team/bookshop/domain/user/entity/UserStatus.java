package org.team.bookshop.domain.user.entity;


public enum UserStatus {
  ACTIVE("ACTIVE", "정상"),
  SUSPENDED("SUSPENDED", "일시정지"),
  BANNED("BANNED", "영구정지"),
  DORMANT("DORMANT", "휴면계정"),
  WITHDRAL("WITHDRAL", "탈퇴요청")
  ;

  private String code;
  private String desc;

  UserStatus(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}