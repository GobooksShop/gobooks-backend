package org.team.bookshop.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.team.bookshop.domain.order.dto.OrderItemResponse;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.global.util.BaseEntity;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Product product;

  private int orderCount;
  private int orderPrice;

  public static OrderItem createOrderItem(int orderCount, int orderPrice) {
    return new OrderItem(orderCount, orderPrice);
  }

  public OrderItem(int orderCount, int orderPrice) {
    this.orderCount = orderCount;
    this.orderPrice = orderPrice;
  }

  public OrderItemResponse toOrderItemResponse() {
    return new OrderItemResponse(id, product.getId(), product.getTitle(), orderCount, orderPrice);
  }
}
