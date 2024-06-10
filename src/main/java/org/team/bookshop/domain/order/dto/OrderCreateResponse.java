package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.order.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCreateResponse {

    private Long orderId;
    private OrderItemResponses orderItemResponses;
    private OrderStatus orderStatus;
    private OrderDeliveryResponse orderDeliveryResponse;

    public OrderCreateResponse(Long orderId, OrderItemResponses orderItemResponses, OrderStatus orderStatus, OrderDeliveryResponse orderDeliveryResponse) {
        this.orderId = orderId;
        this.orderItemResponses = orderItemResponses;
        this.orderStatus = orderStatus;
        this.orderDeliveryResponse = orderDeliveryResponse;
    }

    public OrderCreateResponse() {
    }
}
