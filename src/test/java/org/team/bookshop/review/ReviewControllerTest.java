//package org.team.bookshop.review;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.team.bookshop.domain.product.entity.Product;
//import org.team.bookshop.domain.review.controller.ReviewController;
//import org.team.bookshop.domain.review.dto.ReviewRequestDto;
//import org.team.bookshop.domain.review.dto.ReviewResponseDto;
//import org.team.bookshop.domain.review.entity.Review;
//import org.team.bookshop.domain.review.service.ReviewService;
//import org.team.bookshop.domain.user.entity.User;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@WebMvcTest(ReviewController.class)
//public class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReviewService reviewService;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private User user;
//    private Review review;
//    private ReviewRequestDto reviewRequest;
//    private ReviewResponseDto reviewResponse;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
//
//        user = new User();
//        user.setId(1L);
//
//        review = new Review();
//        review.setId(1L);
//        review.setUser(user);
//        review.setProduct(new Product());
//        review.setContent("Great book!");
//        review.setRating(5);
//
//        reviewRequest = new ReviewRequestDto();
//        reviewRequest.setProductId(1L);
//        reviewRequest.setContent("Great book!");
//        reviewRequest.setRating(5);
//
//        reviewResponse = new ReviewResponseDto(review);
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    public void addReviewTest() throws Exception {
//        when(reviewService.addReview(any(Long.class), any(ReviewRequestDto.class)))
//                .thenReturn(reviewResponse);
//
//        mockMvc.perform(post("/api/reviews")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(reviewRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(review.getId()))
//                .andExpect(jsonPath("$.content").value(review.getContent()));
//    }
//
//    @Test
//    public void getReviewsByProductIdTest() throws Exception {
//        List<ReviewResponseDto> reviews = List.of(reviewResponse);
//        when(reviewService.getReviewsByProductId(1L)).thenReturn(reviews);
//
//        mockMvc.perform(get("/api/reviews/product/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].id").value(review.getId()))
//                .andExpect(jsonPath("$[0].content").value(review.getContent()));
//    }
//}