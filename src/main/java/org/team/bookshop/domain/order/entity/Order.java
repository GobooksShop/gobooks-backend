package org.team.bookshop.domain.order.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;
import org.team.bookshop.domain.order.dto.OrderAbstractResponse;
import org.team.bookshop.domain.order.dto.OrderCreateResponse;
import org.team.bookshop.domain.order.dto.OrderItemResponses;
import org.team.bookshop.domain.order.dto.OrderUpdateRequest;
import org.team.bookshop.domain.order.dto.OrderUpdateResponse;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.global.util.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name="orders", indexes = {
    @Index(name = "idx__unique__merchantUid", columnList = "merchantUid", unique = true)
})
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @Column(unique = true)
  private String merchantUid;

  private LocalDateTime orderDateTime;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private int orderTotalPrice;
  private int orderTotalAmount;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  public static Order createOrder() {
      return new Order();
  }

  public static Order notExistingOrder() {
    Order order = new Order();
    order.merchantUid = "xxx";
    return order;
  }

  public OrderCreateResponse toOrderCreateResponse() {
    return new OrderCreateResponse(
            id,
            merchantUid,
            new OrderItemResponses(orderItems.stream().map(OrderItem::toOrderItemResponse).collect(Collectors.toList())),
            orderStatus,
            orderTotalPrice);
  }

//  public OrderUpdateResponse toOrderUpdateResponse() {
//    return new OrderUpdateResponse(id,
//            new OrderItemResponses(orderItems.stream().map(OrderItem::toOrderItemResponse).collect(Collectors.toList())),
//            orderStatus,
//            delivery.toOrderDeliveryResponse());
//  }

  // User의 Order내역들을 보여줄 때 각 order들을 나타내기 위한 간략한 정보
  public OrderAbstractResponse toOrderAbstractResponse() {
    return new OrderAbstractResponse(id,
        orderDateTime,
        orderTotalPrice,
        orderTotalAmount,
        orderItems.get(0).getProduct().getTitle(),
        orderItems.get(0).getProduct().getPictureUrl());
  }

  public void changeOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
}
