package org.team.bookshop.domain.product.response;

import lombok.Getter;
import org.team.bookshop.domain.product.entity.Product;

@Getter
public class ProductResponse {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private String author;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.author = product.getAuthor();
    }
}
