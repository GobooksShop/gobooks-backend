package org.team.bookshop.domain.category.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
//    if (parent.getId().equals(children.getId())) {
//      throw new ApiException(ErrorCode.SELF_LOOP_CATEGORY_PATH);
//    }
    this.parent = parent;
    this.children = children;
  }

  // Equals and HashCode
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryPathId that = (CategoryPathId) o;
    return Objects.equals(parent != null ? parent.getId() : null,
        that.parent != null ? that.parent.getId() : null) &&
        Objects.equals(children != null ? children.getId() : null,
            that.children != null ? that.children.getId() : null);
  }


  @Override
  public int hashCode() {
    return Objects.hash(parent != null ? parent.getId() : null,
        children != null ? children.getId() : null);
  }
}
