package org.team.bookshop.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.team.bookshop.domain.review.dto.ReviewRequestDto;
import org.team.bookshop.domain.review.dto.ReviewResponseDto;
import org.team.bookshop.domain.review.service.ReviewService;
import org.team.bookshop.domain.user.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponseDto> addReview(@AuthenticationPrincipal User user,
                                                       @RequestBody @Valid ReviewRequestDto reviewRequest) {
        var savedReview = reviewService.addReview(user.getId(), reviewRequest);
        return ResponseEntity.status(201).body(savedReview);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByProductId(@PathVariable Long productId) {
        var reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
}