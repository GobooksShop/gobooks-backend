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

    private List<OrderItemRequest> orderItemRequests;
    private OrderAddressDto orderAddressDto;

    public OrderRequest() {
    }

    public OrderRequest(List<OrderItemRequest> orderItemRequests, OrderAddressDto orderAddressDto) {
        this.orderItemRequests = orderItemRequests;
        this.orderAddressDto = orderAddressDto;
    }
}
