package org.team.bookshop.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.product.entity.Product;
import lombok.Builder;

@Getter
@NoArgsConstructor
public class ProductSaveRequestDto {

    @Setter
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Fixed price is required")
    private int fixedPrice;

    @NotNull(message = "Publication year is required")
    private LocalDate publicationYear;

    @NotNull(message = "Status is required")
    private Product.Status status;

    @NotNull(message = "Discount is required")
    private boolean discount;

    private int stockQuantity;

    @NotNull(message = "Category IDs are required")
    private List<Long> categoryIds;

    @Builder
    public ProductSaveRequestDto(Long id, String title, String author, String isbn, String content, int fixedPrice,
                                 LocalDate publicationYear, Product.Status status, boolean discount, int stockQuantity,
                                 List<Long> categoryIds) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.content = content;
        this.fixedPrice = fixedPrice;
        this.publicationYear = publicationYear;
        this.status = status;
        this.discount = discount;
        this.stockQuantity = stockQuantity;
        this.categoryIds = categoryIds;
    }

    public Product toEntity() {
        return Product.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .content(content)
                .fixedPrice(fixedPrice)
                .publicationYear(publicationYear)
                .status(Product.Status.AVAILABLE)
                .stockQuantity(stockQuantity)
                .discount(discount)
                .build();
    }
}