package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.order.enums.OrderStatus;

import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;
    private List<OrderItemResponse> orderItemResponses;
    private OrderStatus orderStatus;
    private OrderAddressResponse orderAddressResponse;
    private int orderTotalPrice;
    private int orderTotalAmount;

    public OrderResponse() {
    }

    public OrderResponse(Long orderId, List<OrderItemResponse> orderItemResponses, OrderStatus orderStatus, OrderAddressResponse orderAddressResponse, int orderTotalPrice, int orderTotalAmount) {
        this.orderId = orderId;
        this.orderItemResponses = orderItemResponses;
        this.orderStatus = orderStatus;
        this.orderAddressResponse = orderAddressResponse;
        this.orderTotalPrice = orderTotalPrice;
        this.orderTotalAmount = orderTotalAmount;
    }
}
