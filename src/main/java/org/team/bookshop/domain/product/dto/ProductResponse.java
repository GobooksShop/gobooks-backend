package org.team.bookshop.domain.product.dto;

import java.time.LocalDate;
import lombok.Getter;
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

  public ProductResponse(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.author = product.getAuthor();
    this.isbn = product.getIsbn();
    this.content = product.getContent();
    this.fixedPrice = product.getFixedPrice();
    this.publicationYear = product.getPublicationYear();
    this.status = product.getStatus();
  }
}
