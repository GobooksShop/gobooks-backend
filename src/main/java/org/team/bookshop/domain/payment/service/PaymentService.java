package org.team.bookshop.domain.payment.service;

import org.springframework.stereotype.Service;
import org.team.bookshop.domain.order.dto.OrderCreateRequest;
import org.team.bookshop.domain.order.dto.OrderCreateResponse;
import org.team.bookshop.domain.order.dto.OrderItemResponse;

@Service
public class PaymentService {

  public OrderItemResponse complatePayment(OrderCreateRequest request) {
    OrderCreateResponse response = new OrderCreateResponse();

    return null;
  }
}
