package org.team.bookshop.domain.order.dto;

import java.util.List;

public class OrderListResponse {
    private List<OrderAbstractResponse> orderAbstractResponses;

    public OrderListResponse() {
    }

    public OrderListResponse(List<OrderAbstractResponse> orderAbstractResponses) {
        this.orderAbstractResponses = orderAbstractResponses;
    }
}
