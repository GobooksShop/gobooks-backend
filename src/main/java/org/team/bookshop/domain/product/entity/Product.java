package org.team.bookshop.domain.product.entity;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.Column;
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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", updatable = false)
    private Long book_id;

    @Column(name = "title", nullable = false)
    private Long title;

    @Column(name = "publication_year", nullable = false)
    private Date publicationYear;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", nullable = false)
    private Long isbn;



    @Column(name = "fixed_price", nullable = false)
    private int fixed_price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Product(String author, String title, Long isbn) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}