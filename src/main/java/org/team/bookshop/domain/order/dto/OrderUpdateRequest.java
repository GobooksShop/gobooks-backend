package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.user.entity.Address;

@Data
public class OrderUpdateRequest {
    private Long orderId;

    private OrderAddressUpdate orderAddressUpdate;

    public OrderUpdateRequest() {
    }

    public OrderUpdateRequest(Long orderId, OrderAddressUpdate orderAddressUpdate) {
        this.orderId = orderId;
        this.orderAddressUpdate = orderAddressUpdate;
    }
}
