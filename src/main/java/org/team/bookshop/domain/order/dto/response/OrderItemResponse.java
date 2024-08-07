package org.team.bookshop.domain.order.dto.response;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long orderItemId;
    private Long productId;
    private String productName;
    private int orderCount;
    private int orderPrice;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long orderItemId, Long productId, String productName, int orderCount, int orderPrice) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.productName = productName;
        this.orderCount = orderCount;
        this.orderPrice = orderPrice;
    }
}
