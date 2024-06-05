package org.team.bookshop.domain.order.dto;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long productId;
    private String productName;
    private int orderCount;
    private int orderPrice;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long productId, String productName, int orderCount, int orderPrice) {
        this.productId = productId;
        this.productName = productName;
        this.orderCount = orderCount;
        this.orderPrice = orderPrice;
    }
}
