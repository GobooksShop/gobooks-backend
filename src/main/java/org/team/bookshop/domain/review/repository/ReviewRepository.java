package org.team.bookshop.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
}
