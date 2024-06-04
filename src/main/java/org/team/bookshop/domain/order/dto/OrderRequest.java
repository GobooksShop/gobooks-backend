package org.team.bookshop.domain.order.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {

    private Long userId;

    private List<OrderItemRequest> orderItemRequests;

    public OrderRequest() {
    }

    public OrderRequest(Long userId, List<OrderItemRequest> orderItemRequests) {
        this.userId = userId;
        this.orderItemRequests = orderItemRequests;
    }
}
