package org.team.bookshop.domain.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.product.entity.Product;

@Getter
@NoArgsConstructor
public class ProductDto {

  private Long id;
  private String title;
  private String author;
  private int fixedPrice;
  private String pictureUrl;
  private int stockQuantity;

  @QueryProjection
  public ProductDto(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.author = product.getAuthor();
    this.fixedPrice = product.getFixedPrice();
    this.pictureUrl = product.getPictureUrl();
    this.stockQuantity = product.getStockQuantity();
  }

  public ProductDto(ProductDto productDto) {
    this.id = productDto.getId();
    this.title = productDto.getTitle();
    this.author = productDto.getAuthor();
    this.fixedPrice = productDto.getFixedPrice();
    this.pictureUrl = productDto.getPictureUrl();
    this.stockQuantity = productDto.getStockQuantity();
  }
}
