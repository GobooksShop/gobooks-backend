package org.team.bookshop.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.domain.product.entity.Product;

@Getter
@Setter
public class AddProductRequest {

  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Author is required")
  private String author;

  @NotBlank(message = "ISBN is required")
  private String isbn;

  @NotBlank(message = "Content is required")
  private String content;

  @NotNull(message = "Fixed price is required")
  private int fixedPrice;

  @NotNull(message = "Publication year is required")
  private LocalDate publicationYear;

  @NotNull(message = "Category IDs are required")
  private List<Long> categoryIds;

  public Product toEntity() {
    return Product.builder()
        .title(title)
        .author(author)
        .isbn(isbn)
        .content(content)
        .fixedPrice(fixedPrice)
        .publicationYear(publicationYear)
        .status(Product.Status.AVAILABLE)
        .build();
  }
}
