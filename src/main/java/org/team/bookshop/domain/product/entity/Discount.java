package org.team.bookshop.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.awt.print.Book;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "discount_id")
  private Long id;

  private double discountRate;
  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  @OneToOne
  @JoinColumn(name = "book_id")
  private Book book;
}
