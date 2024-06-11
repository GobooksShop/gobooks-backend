package org.team.bookshop.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.dto.*;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.domain.product.service.ProductService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductRepository productRepository;

    @GetMapping("/testInit")
    @Transactional
    public void init() {
        Product product1 = new Product("제목1", "작가1", "isbn1", "책1 내용입니다", 20000, LocalDate.ofYearDay(2020, 15), Product.Status.AVAILABLE, 20, "123");
        Product product2 = new Product("제목2", "작가2", "isbn2", "책2 내용입니다", 30000, LocalDate.ofYearDay(2024, 30), Product.Status.AVAILABLE, 30, "4567");

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
    }

    // 주문 생성
    @PostMapping("/create")
    public ResponseEntity<OrderCreateResponse> createOrder(
            @RequestBody OrderCreateRequest orderCreateRequest) {
        Long savedOrderId = orderService.save(orderCreateRequest);
        System.out.println("savedOrderId = " + savedOrderId);
        OrderCreateResponse orderCreateResponse = orderService.findByIdForCreateResponse(savedOrderId).toOrderCreateResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreateResponse);
    }

    // 주문 수정
    @PatchMapping("/update")
    public ResponseEntity<OrderUpdateResponse> updateOrder(
            @RequestBody OrderUpdateRequest orderUpdateRequest) {
        Long updatedOrderId = orderService.update(orderUpdateRequest);
        OrderUpdateResponse orderUpdateResponse = orderService.findByIdForCreateResponse(updatedOrderId).toOrderUpdateResponse();

        return ResponseEntity.status(HttpStatus.OK).body(orderUpdateResponse);
    }

    // 주문 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<OrderDeleteResponse> deleteOrder(
            @RequestBody OrderDeleteRequest orderDeleteRequest) {
        Long deletedOrderId = orderService.delete(orderDeleteRequest);
        OrderDeleteResponse orderDeleteResponse = new OrderDeleteResponse(deletedOrderId, true);

        return new ResponseEntity<>(orderDeleteResponse, HttpStatus.OK);
    }
}
