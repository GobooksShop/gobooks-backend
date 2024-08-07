package org.team.bookshop.domain.order.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.dto.response.OrderAbstractResponse;
import org.team.bookshop.domain.order.dto.request.OrderCreateRequest;
import org.team.bookshop.domain.order.dto.response.OrderCreateResponse;
import org.team.bookshop.domain.order.dto.response.OrderResponse;
import org.team.bookshop.domain.order.dto.request.OrderUpdateRequest;
import org.team.bookshop.domain.product.repository.ProductRepository;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  // 주문 생성
  @PostMapping("/create")
  public ResponseEntity<OrderCreateResponse> createOrder(
      @RequestBody OrderCreateRequest orderCreateRequest) {
    Long savedOrderId = orderService.save(orderCreateRequest);
    OrderCreateResponse orderCreateResponse = orderService.findByIdForCreateResponse(savedOrderId);

    return ResponseEntity.status(HttpStatus.CREATED).body(orderCreateResponse);
  }

  // 주문 수정
  @PatchMapping("/update")
  public ResponseEntity<OrderResponse> updateOrder(
      @RequestBody OrderUpdateRequest orderUpdateRequest) {
    String merchantUid = orderService.update(orderUpdateRequest);

    return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDetail(merchantUid));
  }

  // 주문 삭제
  @DeleteMapping("/delete/{orderId}")
  public ResponseEntity<Void> deleteOrder(
      @PathVariable("orderId") Long orderId) {
    Long deletedOrderId = orderService.delete(orderId);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderAbstractResponse>> getUsersOrdersList(
      @PathVariable("userId") Long userId
  ) {
    List<OrderAbstractResponse> orderListResponse = orderService.findByUserIdForOrderListResponse(userId);
    return ResponseEntity.status(HttpStatus.OK).body(orderListResponse);
  }

  @GetMapping("/{merchantUid}")
  public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable("merchantUid") String merchantUid) {
    OrderResponse orderDetail = orderService.getOrderDetail(merchantUid);
    return ResponseEntity.status(HttpStatus.OK).body(orderDetail);
  }

}
