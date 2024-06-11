package org.team.bookshop.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.team.bookshop.domain.category.dto.SimpleCategoryResponseDto;
import org.team.bookshop.domain.product.entity.Product;

@Getter
public class ProductResponse {

  private final Long id;
  private final String title;
  private final String author;
  private final String isbn;
  private final String content;
  private final int fixedPrice;
  private final LocalDate publicationYear;
  private final Product.Status status;
  private final String pictureUrl;


  @JsonIgnoreProperties("bookCategories")
  private List<SimpleCategoryResponseDto> categories;

  public ProductResponse(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.author = product.getAuthor();
    this.isbn = product.getIsbn();
    this.content = product.getContent();
    this.fixedPrice = product.getFixedPrice();
    this.publicationYear = product.getPublicationYear();
    this.status = product.getStatus();
    this.pictureUrl = product.getPictureUrl();

  }

  public static ProductResponse fromEntity(Product product) {
    ProductResponse dto = new ProductResponse(product);

    dto.categories = product.getBookCategories().stream()
        .map(bookCategory -> new SimpleCategoryResponseDto(bookCategory.getCategory()))
        .collect(Collectors.toList());

    return dto;
  }
}
