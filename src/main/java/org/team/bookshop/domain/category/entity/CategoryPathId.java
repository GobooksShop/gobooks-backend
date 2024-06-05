package org.team.bookshop.domain.category.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CategoryPathId implements Serializable {

  @ManyToOne
  private Category parent;

  @ManyToOne
  private Category children;

  public CategoryPathId(Category parent, Category children) {
    if (parent.getId().equals(children.getId())) {
      throw new ApiException(ErrorCode.SELF_LOOP_CATEGORY_PATH);
    }
    this.parent = parent;
    this.children = children;
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
    return Objects.equals(parent, that.parent) && Objects.equals(children, that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parent, children);
  }
}
