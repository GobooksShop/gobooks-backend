package org.team.bookshop.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.global.util.BaseEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Address extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long addressId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private String label;
  private Boolean isPrimary;
  private String zipcode;
  private String address1;
  private String address2;
  private String recipientName;
  private String recipientPhone;

}
