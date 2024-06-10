package org.team.bookshop.domain.order.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;
import org.team.bookshop.domain.order.dto.OrderCreateResponse;
import org.team.bookshop.domain.order.dto.OrderUpdateResponse;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.global.util.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name="orders")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  private LocalDateTime orderDateTime;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private int orderTotalPrice;
  private int orderTotalAmount;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  public static Order createOrder() {
      return new Order();
  }

  public OrderCreateResponse toOrderCreateResponse() {
    return new OrderCreateResponse(id,
            orderItems.stream().map(oi -> oi.toOrderItemResponse()).collect(Collectors.toList()),
            orderStatus,
            delivery.toOrderDeliveryResponse());
  }

  public OrderUpdateResponse toOrderUpdateResponse() {
    return new OrderUpdateResponse(id,
            orderItems.stream().map(oi -> oi.toOrderItemResponse()).collect(Collectors.toList()),
            orderStatus,
            delivery.toOrderDeliveryResponse());
  }
}
