package org.team.bookshop.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.domain.review.dto.ReviewRequestDto;
import org.team.bookshop.domain.review.dto.ReviewResponseDto;
import org.team.bookshop.domain.review.entity.Review;
import org.team.bookshop.domain.review.repository.ReviewRepository;
import org.team.bookshop.domain.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto addReview(Long userId, ReviewRequestDto reviewRequest) {
        var product = productRepository.findById(reviewRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean hasPurchased = orderRepository.existsOrderByByUserIdAndProductId(userId, product.getId());
        if (!hasPurchased) {
            throw new RuntimeException("User has not purchased this product");
        }

        var review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setContent(reviewRequest.getContent());
        review.setRating(reviewRequest.getRating());

        var savedReview = reviewRepository.save(review);
        return new ReviewResponseDto(savedReview);
    }

    public List<ReviewResponseDto> getReviewsByProductId(Long productId) {
        var reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }
}