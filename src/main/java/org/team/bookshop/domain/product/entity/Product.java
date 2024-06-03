package org.team.bookshop.domain.product.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {

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
    private String isbn;

    @Column(name = "fixed_price", nullable = false)
    private int fixedPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Product(String author, String title, String isbn, String content, int fixedPrice, Date publicationYear, Status status) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.content = content;
        this.fixedPrice = fixedPrice;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public enum Status {
        AVAILABLE,
        UNAVAILABLE
    }
}