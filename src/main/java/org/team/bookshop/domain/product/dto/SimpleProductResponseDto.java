package org.team.bookshop.domain.product.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.team.bookshop.domain.product.entity.Product;

@Getter
@AllArgsConstructor
public class SimpleProductResponseDto {

  private Long id;
  private String title;
  private String author;
  private String isbn;
  private String content;
  private int fixedPrice;
  private LocalDate publicationYear;
  private Product.Status status;
}
