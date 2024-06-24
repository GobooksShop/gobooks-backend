package org.team.bookshop.domain.product.dto;

import lombok.Getter;

@Getter
public class ProductResponseMainDto {

    private final Long id;
    private final String title;
    private final String author;
    private final String pictureUrl;

    public ProductResponseMainDto(Long id, String title, String author, String pictureUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pictureUrl = pictureUrl;
    }
}
