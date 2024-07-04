package org.team.bookshop.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private Long productId;
    private String content;
    private int rating;
}
