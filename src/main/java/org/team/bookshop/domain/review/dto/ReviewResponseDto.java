package org.team.bookshop.domain.review.dto;

import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.domain.review.entity.Review;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private String content;
    private int rating;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.productId = review.getProduct().getId();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}
