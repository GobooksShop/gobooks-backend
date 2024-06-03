package org.team.bookshop.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.global.util.BaseEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Address extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long addressId;
  private String label;
  private Boolean isPrimary;
  private String zipcode;
  private String address1;
  private String address2;
  private String recipientName;
  private String recipientPhone;

}
