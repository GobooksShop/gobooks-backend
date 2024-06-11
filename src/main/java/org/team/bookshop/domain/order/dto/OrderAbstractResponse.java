package org.team.bookshop.domain.order.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderAbstractResponse {
    private Long orderId;
    private LocalDateTime orderDateTime;
    private int orderTotalPrice;
    private int orderTotalAmount;

    private String representativeOrderItemName;
    private String representativeOrderItemImageUrl;

    public OrderAbstractResponse(Long orderId, LocalDateTime orderDateTime, int orderTotalPrice, int orderTotalAmount, String representativeOrderItemName, String representativeOrderItemImageUrl) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.orderTotalPrice = orderTotalPrice;
        this.orderTotalAmount = orderTotalAmount;
        this.representativeOrderItemName = representativeOrderItemName;
        this.representativeOrderItemImageUrl = representativeOrderItemImageUrl;
    }

    public OrderAbstractResponse() {
    }
}
