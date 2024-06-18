package org.team.bookshop.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.dto.OrderCreateRequest;
import org.team.bookshop.domain.order.dto.OrderCreateResponse;
import org.team.bookshop.domain.order.dto.OrderUpdateRequest;
import org.team.bookshop.domain.order.dto.OrderUpdateResponse;
import org.team.bookshop.domain.product.repository.ProductRepository;

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
    OrderCreateResponse orderCreateResponse = orderService.findByIdForCreateResponse(savedOrderId)
        .toOrderCreateResponse();

    return ResponseEntity.status(HttpStatus.CREATED).body(orderCreateResponse);
  }

  // 주문 수정
  @PatchMapping("/update")
  public ResponseEntity<OrderUpdateResponse> updateOrder(
      @RequestBody OrderUpdateRequest orderUpdateRequest) {
    Long updatedOrderId = orderService.update(orderUpdateRequest).getOrderId();
    OrderUpdateResponse orderUpdateResponse = orderService.findByIdForCreateResponse(updatedOrderId)
        .toOrderUpdateResponse();

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
