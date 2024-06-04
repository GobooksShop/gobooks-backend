package org.team.bookshop.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryPath {

  @EmbeddedId
  private CategoryPathId id;

  @Column(name = "depth")
  private Long depth;

  public CategoryPath(CategoryPathId id) {
    this.id = id;
  }

  public CategoryPath(Category parent, Category child) {
    this.id = new CategoryPathId(parent, child);
  }

  public Category getParent() {
    return id.getParent();
  }

  public Category getchild() {
    return id.getChild();
  }
}
