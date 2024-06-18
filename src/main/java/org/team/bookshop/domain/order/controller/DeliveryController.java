package org.team.bookshop.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.order.Service.DeliveryService;
import org.team.bookshop.domain.order.dto.CreateDeliveryRequest;
import org.team.bookshop.domain.order.repository.DeliveryRepository;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.AddressRepository;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;
import org.team.bookshop.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

  private final DeliveryService deliveryService;

  @PostMapping("/create")
  public ResponseEntity<? extends ApiResponse<?>> createDelivery(@RequestBody CreateDeliveryRequest createDeliveryRequest) {
    deliveryService.createDelivery(createDeliveryRequest);
    return ResponseEntity.ok(ApiResponse.success());
  }

}
