package org.team.bookshop.domain.category.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Embeddable
@Getter
@Setter
public class CategoryPathId implements Serializable {

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Category parent;

  @ManyToOne
  @JoinColumn(name = "child_id")
  private Category child;

  public CategoryPathId() {
  }

  public CategoryPathId(Category parent, Category child) {
    if (parent.getId().equals(child.getId())) {
      throw new ApiException(ErrorCode.SELF_LOOP_CATEGORY_PATH);
    }
    this.parent = parent;
    this.child = child;
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
    return Objects.equals(parent, that.parent) && Objects.equals(child, that.child);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parent, child);
  }
}
