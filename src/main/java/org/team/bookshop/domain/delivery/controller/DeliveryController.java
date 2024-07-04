package org.team.bookshop.domain.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.delivery.service.DeliveryService;
import org.team.bookshop.domain.delivery.dto.CreateDeliveryRequest;
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
