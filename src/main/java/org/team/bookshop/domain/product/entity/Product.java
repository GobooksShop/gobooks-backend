package org.team.bookshop.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.team.bookshop.global.util.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "book")
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id", updatable = false)
  private Long bookId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "publication_year", nullable = false)
  private Date publicationYear;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "isbn", nullable = false)
  private Long isbn;


  @Column(name = "fixed_price", nullable = false)
  private int fixed_price;

  @OneToOne(fetch = FetchType.LAZY)
  private Discount discount;

  @Builder
  public Product(String author, String title, Long isbn) {
    this.author = author;
    this.title = title;
    this.isbn = isbn;
  }

  public void update(String title) {
    this.title = title;

  }


}