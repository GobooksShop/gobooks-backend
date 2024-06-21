package org.team.bookshop.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.payment.dto.PaymentResponse;

@Data
@Builder
public class OrderResponse {

  private Long orderId;

  private String merchantUid;

  private List<OrderItemResponse> orderItemResponses;

  private OrderStatus orderStatus;

  private String orderDateTime;

  private OrderDeliveryResponse orderDeliveryResponse;

  private int orderTotalPrice;

  private int orderTotalAmount;

  private PaymentResponse paymentResponse;

  public OrderResponse() {
  }

  public OrderResponse(Long orderId, String merchantUid, List<OrderItemResponse> orderItemResponses,
      OrderStatus orderStatus, String orderDateTime, OrderDeliveryResponse orderDeliveryResponse, int orderTotalPrice,
      int orderTotalAmount, PaymentResponse paymentResponse) {
    this.orderId = orderId;
    this.merchantUid = merchantUid;
    this.orderItemResponses = orderItemResponses;
    this.orderStatus = orderStatus;
    this.orderDateTime = orderDateTime;
    this.orderDeliveryResponse = orderDeliveryResponse;
    this.orderTotalPrice = orderTotalPrice;
    this.orderTotalAmount = orderTotalAmount;
    this.paymentResponse = paymentResponse;
  }
}
