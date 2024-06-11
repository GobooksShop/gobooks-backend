package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.order.enums.OrderStatus;

@Data
public class OrderAddressResponse {
    private String zipcode;
    private String address1;
    private String address2;
    private String recipientName;
    private String recipientPhone;
    private OrderStatus orderStatus;

    public OrderAddressResponse() {
    }

    public OrderAddressResponse(String zipcode, String address1, String address2, String recipientName, String recipientPhone, OrderStatus orderStatus) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.orderStatus = orderStatus;
    }
}
