package org.team.bookshop.domain.order.dto;

import lombok.Data;

@Data
public class OrderDeleteRequest {
    private Long orderId;

    public OrderDeleteRequest(Long orderId) {
        this.orderId = orderId;
    }

    public OrderDeleteRequest() {
    }
}
