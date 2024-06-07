package org.team.bookshop.domain.category.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.product.entity.Product;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategory {

  @EmbeddedId
  private BookCategoryId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("bookId")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("categoryId")
  private Category category;
}
