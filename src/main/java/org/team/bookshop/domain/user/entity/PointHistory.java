package org.team.bookshop.domain.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
public class PointHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "point_id")
  private Point point;

  private Long points;
  private String description;
  private LocalDateTime createDate;
  private LocalDateTime expireDate;
}
