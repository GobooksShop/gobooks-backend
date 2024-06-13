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
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable("orderId") Long orderId) {
        Long deletedOrderId = orderService.delete(orderId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
