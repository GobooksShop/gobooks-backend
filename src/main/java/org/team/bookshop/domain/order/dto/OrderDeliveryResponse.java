package org.team.bookshop.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.team.bookshop.domain.order.enums.DeliveryStatus;

@Data
public class OrderDeliveryResponse {

    private String label;
    private String zipcode;
    private String address1;
    private String address2;
    private String recipientName;
    private String recipientPhone;
    private DeliveryStatus deliveryStatus;

    public OrderDeliveryResponse() {
    }

    public OrderDeliveryResponse(String label, String zipcode, String address1, String address2, String recipientName, String recipientPhone, DeliveryStatus deliveryStatus) {
        this.label = label;
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.deliveryStatus = deliveryStatus;
    }
}
