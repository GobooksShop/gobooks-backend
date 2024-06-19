package org.team.bookshop.domain.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.team.bookshop.domain.order.enums.OrderStatus;

@Data
@Builder
public class OrderResponse {

  private Long orderId;

  private String merchantUid;

  private List<OrderItemResponse> orderItemResponses;

  private OrderStatus orderStatus;

  private OrderAddressResponse orderAddressResponse;

  private int orderTotalPrice;

  private int orderTotalAmount;

  public OrderResponse() {
  }

  public OrderResponse(Long orderId, String merchantUid, List<OrderItemResponse> orderItemResponses,
      OrderStatus orderStatus, OrderAddressResponse orderAddressResponse, int orderTotalPrice,
      int orderTotalAmount) {
    this.orderId = orderId;
    this.merchantUid = merchantUid;
    this.orderItemResponses = orderItemResponses;
    this.orderStatus = orderStatus;
    this.orderAddressResponse = orderAddressResponse;
    this.orderTotalPrice = orderTotalPrice;
    this.orderTotalAmount = orderTotalAmount;
  }
}
