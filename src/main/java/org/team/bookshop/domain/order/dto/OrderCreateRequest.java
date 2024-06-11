package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.entity.OrderItem;
import org.team.bookshop.domain.order.enums.DeliveryStatus;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.user.entity.Address;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderCreateRequest {


    List<OrderItemRequest> orderItemRequests;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(List<OrderItemRequest> orderItemRequests) {
        this.orderItemRequests = orderItemRequests;
    }


    public List<OrderItem> toOrderItems() {
        return orderItemRequests.stream().map(oir -> oir.toOrderItemEntity()).collect(Collectors.toList());
    }

    public List<Long> toProductIds() {
        return orderItemRequests.stream().map(oir -> oir.getProductId()).collect(Collectors.toList());
    }
}
