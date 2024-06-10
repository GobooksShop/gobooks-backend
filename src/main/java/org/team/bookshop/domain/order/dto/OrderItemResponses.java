package org.team.bookshop.domain.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemResponses {
    List<OrderItemResponse> orderItemResponses;

    public OrderItemResponses(List<OrderItemResponse> orderItemResponses) {
        this.orderItemResponses = orderItemResponses;
    }

    public OrderItemResponses() {
    }
}
