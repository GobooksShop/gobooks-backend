package org.team.bookshop.domain.delivery.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.*;
import org.team.bookshop.domain.order.dto.request.OrderAddressUpdate;
import org.team.bookshop.domain.order.dto.response.OrderDeliveryResponse;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.enums.DeliveryStatus;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.global.util.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class Delivery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  private LocalDate deliveryStart;
  private LocalDate deliveryComp;
  private Long trackingNumber;

  // 배송정보 저장 시 필요한 주소정보
  private String label;
  private String zipcode;
  private String address1;
  private String address2;
  private String recipientName;
  private String recipientPhone;

  public static Delivery createDelivery(Order order, DeliveryStatus deliveryStatus, LocalDate deliveryStart, Long trackingNumber) {
    return new Delivery(order, deliveryStatus, deliveryStart, trackingNumber);
  }

  public Delivery(Order order, DeliveryStatus deliveryStatus, LocalDate deliveryStart, Long trackingNumber) {
    order.setDelivery(this);
    this.deliveryStatus = deliveryStatus;
    this.deliveryStart = deliveryStart;
    this.trackingNumber = trackingNumber;
  }

  public OrderDeliveryResponse toOrderDeliveryResponse() {
    return new OrderDeliveryResponse(
        label,
        zipcode,
        address1,
        address2,
        recipientName,
        recipientPhone,
        deliveryStatus);
  }

  public void fillAddressInformation(Address transferedAddress) {
    label = transferedAddress.getLabel();
    zipcode = transferedAddress.getZipcode();
    address1 = transferedAddress.getAddress1();
    address2 = transferedAddress.getAddress2();
    recipientName = transferedAddress.getRecipientName();
    recipientPhone = transferedAddress.getRecipientPhone();
  }

  public void updateAddressByOrderUpdateRequest(OrderAddressUpdate orderAddressUpdate) {
    label = orderAddressUpdate.getLabel();
    zipcode = orderAddressUpdate.getZipcode();
    address1 = orderAddressUpdate.getAddress1();
    address2 = orderAddressUpdate.getAddress2();
    recipientName = orderAddressUpdate.getRecipientName();
    recipientPhone = orderAddressUpdate.getRecipientPhone();
  }
}
