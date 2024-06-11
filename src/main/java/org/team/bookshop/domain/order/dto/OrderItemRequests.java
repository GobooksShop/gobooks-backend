package org.team.bookshop.domain.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemRequests {
    List<OrderItemRequest> orderItemRequests;

    public OrderItemRequests() {
    }

    public OrderItemRequests(List<OrderItemRequest> orderItemRequests) {
        this.orderItemRequests = orderItemRequests;
    }
}
