package org.team.bookshop.domain.category.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CategoryPathId implements Serializable {

  @ManyToOne
  @JoinColumn(name = "ancestor")
  private Category ancestor;

  @ManyToOne
  @JoinColumn(name = "descendant")
  private Category descendant;

  public CategoryPathId() {
  }

  public CategoryPathId(Category ancestor, Category descendant) {
    this.ancestor = ancestor;
    this.descendant = descendant;
  }

  // Equals and HashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryPathId that = (CategoryPathId) o;
    return Objects.equals(ancestor, that.ancestor) && Objects.equals(descendant, that.descendant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ancestor, descendant);
  }
}
