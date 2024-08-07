package org.team.bookshop.domain.order.dto.response;

import lombok.Data;
import org.team.bookshop.domain.order.enums.OrderStatus;

@Data
public class OrderCreateResponse {
    private Long orderId;
    private String merchantUid;
    private OrderItemResponses orderItemResponses;
    private OrderStatus orderStatus;
    private int orderTotalPrice;

    public OrderCreateResponse(Long orderId, String merchantUid, OrderItemResponses orderItemResponses, OrderStatus orderStatus, int orderTotalPrice) {
        this.orderId = orderId;
        this.merchantUid = merchantUid;
        this.orderItemResponses = orderItemResponses;
        this.orderStatus = orderStatus;
        this.orderTotalPrice = orderTotalPrice;
    }

    public OrderCreateResponse() {
    }
}
