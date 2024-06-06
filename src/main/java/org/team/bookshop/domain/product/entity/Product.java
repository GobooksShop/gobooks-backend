package org.team.bookshop.domain.product.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter // Add this if you need to mutate fields directly
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int fixedPrice;

    @Column(nullable = false)
    private LocalDate publicationYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private int stockQuantity;

    @Builder
    public Product(String title, String author, String isbn, String content, int fixedPrice, LocalDate publicationYear, Status status, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.content = content;
        this.fixedPrice = fixedPrice;
        this.publicationYear = publicationYear;
        this.status = status;
        this.stockQuantity = stockQuantity;
    }

    public enum Status {
        AVAILABLE, UNAVAILABLE
    }

    public void update(String title, String author, String isbn, String content, int fixedPrice, LocalDate publicationYear, Status status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.content = content;
        this.fixedPrice = fixedPrice;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    public void decreaseStock(int quantity) {
        if(stockQuantity - quantity < 0) {
            throw new IllegalStateException("현재 상품 재고가 부족합니다.");
        }
        stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }
}
