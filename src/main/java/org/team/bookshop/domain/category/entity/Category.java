package org.team.bookshop.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.global.util.BaseEntity;

@Entity
@Getter
@Setter
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "id.parent")
  private List<CategoryPath> parentPaths;

  @OneToMany(mappedBy = "id.child")
  private List<CategoryPath> childPaths;
}
