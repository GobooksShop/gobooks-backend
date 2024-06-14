package org.team.bookshop.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.product.entity.Product;

@Getter
@NoArgsConstructor
public class ProductCreateRequestDto {

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

  @NotNull(message = "Status is required")
  private Product.Status status;

  private int stockQuantity;

  private String pictureUrl;

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
