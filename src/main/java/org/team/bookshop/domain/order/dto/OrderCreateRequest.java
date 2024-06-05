package org.team.bookshop.domain.order.dto;

import lombok.Data;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.enums.DeliveryStatus;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.user.entity.Address;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderCreateRequest {

    private List<OrderItemRequest> orderItemRequests;
    private OrderAddressCreate orderAddressCreate;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(List<OrderItemRequest> orderItemRequests, OrderAddressCreate orderAddressCreate) {
        this.orderItemRequests = orderItemRequests;
        this.orderAddressCreate = orderAddressCreate;
    }

    public Address toAddressEntity() {
        Address address = new Address();
        address.setZipcode(orderAddressCreate.getZipcode());
        address.setAddress1(orderAddressCreate.getAddress1());
        address.setAddress2(orderAddressCreate.getAddress2());
        address.setRecipientName(orderAddressCreate.getRecipientName());
        address.setRecipientPhone(orderAddressCreate.getRecipientPhone());
        return address;
    }

    public Delivery toDeliveryEntity() {
        Delivery delivery = Delivery.createDelivery(DeliveryStatus.READY, LocalDate.now(), 1L);
        return delivery;
    }
}
