package org.team.bookshop.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.dto.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/create")
    public ResponseEntity<OrderCreateResponse> createOrder(OrderCreateRequest orderCreateRequest) {
        Long savedOrderId = orderService.save(orderCreateRequest);
        OrderCreateResponse orderCreateResponse = orderService.findById(savedOrderId).toOrderCreateResponse();

        return new ResponseEntity<>(orderCreateResponse, HttpStatus.CREATED);
    }

    // 주문 수정
    @PatchMapping("/update")
    public ResponseEntity<OrderUpdateResponse> updateOrder(OrderUpdateRequest orderUpdateRequest) {
        Long updatedOrderId = orderService.update(orderUpdateRequest);
        OrderUpdateResponse orderUpdateResponse = orderService.findById(updatedOrderId).toOrderUpdateResponse();

        return new ResponseEntity<>(orderUpdateResponse, HttpStatus.OK);
    }

    // 주문 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<OrderDeleteResponse> deleteOrder(OrderDeleteRequest orderDeleteRequest) {
        Long deletedOrderId = orderService.delete(orderDeleteRequest);
        OrderDeleteResponse orderDeleteResponse = new OrderDeleteResponse(deletedOrderId, true);

        return new ResponseEntity<>(orderDeleteResponse, HttpStatus.OK);
    }
}
