package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.user.entity.Address;

@Data
public class OrderUpdateRequest {
    private Long orderId;
    private Address address;

    public OrderUpdateRequest() {
    }

    public OrderUpdateRequest(Long orderId, Address address) {
        this.orderId = orderId;
        this.address = address;
    }
}
