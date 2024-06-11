package org.team.bookshop.domain.order.dto;

import lombok.Data;

@Data
public class OrderDeleteResponse {

    private Long orderId;
    private boolean isDeleted;

    public OrderDeleteResponse(Long orderId, boolean isDeleted) {
        this.orderId = orderId;
        this.isDeleted = isDeleted;
    }

    public OrderDeleteResponse() {
    }
}
