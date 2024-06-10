package org.team.bookshop.domain.order.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.*;
import org.team.bookshop.domain.order.dto.OrderDeliveryResponse;
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

  @OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
  private Address address;

  public static Delivery createDelivery(DeliveryStatus deliveryStatus, LocalDate deliveryStart, Long trackingNumber) {
    return new Delivery(deliveryStatus, deliveryStart, trackingNumber);
  }

  public Delivery(DeliveryStatus deliveryStatus, LocalDate deliveryStart, Long trackingNumber) {
    this.deliveryStatus = deliveryStatus;
    this.deliveryStart = deliveryStart;
    this.trackingNumber = trackingNumber;
  }

  public OrderDeliveryResponse toOrderDeliveryResponse() {
    return new OrderDeliveryResponse(address.getZipcode(), address.getAddress1(), address.getAddress2(), address.getRecipientName(), address.getRecipientPhone(), deliveryStatus);
  }
}
