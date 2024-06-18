package org.team.bookshop.domain.order.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.entity.OrderItem;
import org.team.bookshop.domain.product.entity.Product;

@Data
public class OrderItemRequest {
    private Long productId;
    private int orderCount;
    private int price;

    public OrderItemRequest() {
    }

    public OrderItemRequest(Long productId, int orderCount, int orderPrice) {
        this.productId = productId;
        this.orderCount = orderCount;
        this.price = orderPrice;
    }

    public OrderItem toEntity() {
        OrderItem orderItem = OrderItem.createOrderItem(orderCount, price);
        return orderItem;
    }

    public OrderItem toOrderItemEntity() {
        return OrderItem.createOrderItem(orderCount, price);
    }
}
