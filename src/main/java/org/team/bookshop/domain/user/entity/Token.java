package org.team.bookshop.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tokens")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String token;
  @Column(nullable = false)
  private String type;
  @Column(nullable = false)
  private LocalDateTime expires;
  @Column(nullable = false)
  private LocalDateTime created;

  @Builder
  public Token(Long id, String token, String type, LocalDateTime expires, LocalDateTime created) {
    this.id = id;
    this.token = token;
    this.type = type;
    this.expires = expires;
    this.created = created;
  }
}
