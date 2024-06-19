package org.team.bookshop.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.category.entity.BookCategory;
import org.team.bookshop.global.util.BaseEntity;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String author;

  @Column(nullable = false)
  private String isbn;

  @Column(nullable = false, columnDefinition = "LONGTEXT")
//  @Lob
  private String content;

  @Column(nullable = false)
  private int fixedPrice;

  @Column(nullable = false)
  private LocalDate publicationYear;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  private final LocalDateTime createdAt = LocalDateTime.now();

  private int stockQuantity;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonIgnoreProperties("product")
  private Set<BookCategory> bookCategories = new HashSet<>();

  private String pictureUrl;

//  @Column(nullable = false)
  private boolean discount;

  @Builder
  public Product(String title, String author, String isbn, String content, int fixedPrice,
      LocalDate publicationYear, Status status, int stockQuantity, String pictureUrl, boolean discount) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.content = content;
    this.fixedPrice = fixedPrice;
    this.publicationYear = publicationYear;
    this.status = status;
    this.stockQuantity = stockQuantity;
    this.pictureUrl = pictureUrl;
    this.discount = discount;
  }

  public enum Status {
    AVAILABLE, UNAVAILABLE
  }

  public void update(String title, String author, String isbn, String content, int fixedPrice,
      LocalDate publicationYear, Status status, boolean discount) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.content = content;
    this.fixedPrice = fixedPrice;
    this.publicationYear = publicationYear;
    this.status = status;
    this.discount = discount;
  }

  public void decreaseStock(int quantity) {
    if (stockQuantity - quantity < 0) {
      throw new IllegalStateException("현재 상품 재고가 부족합니다.");
    }
    stockQuantity -= quantity;
  }

  public void increaseStock(int quantity) {
    stockQuantity += quantity;
  }

  public void addBookCategory(BookCategory bookCategory) {
    bookCategories.add(bookCategory);
    bookCategory.setProduct(this); // 양방향 관계 설정
  }

  public int getPrice() {
    if (discount) {
      return (int) (fixedPrice * 0.9);
    } else {
      return fixedPrice;
    }
  }
}
