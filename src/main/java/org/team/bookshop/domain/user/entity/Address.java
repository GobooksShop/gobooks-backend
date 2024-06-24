package org.team.bookshop.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.order.dto.OrderAddressUpdate;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.global.util.BaseEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="address", indexes = {
    @Index(name = "idx__user__label", columnList = "user_id, label")
})
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String label;
    private Boolean isPrimary;
    private String zipcode;
    private String address1;
    private String address2;
    private String recipientName;
    private String recipientPhone;


    public void update(OrderAddressUpdate orderAddressUpdate) {
        label = orderAddressUpdate.getLabel();
        isPrimary = false;
        zipcode = orderAddressUpdate.getZipcode();
        address1 = orderAddressUpdate.getAddress1();
        address2 = orderAddressUpdate.getAddress2();
        recipientName = orderAddressUpdate.getRecipientName();
        recipientPhone = orderAddressUpdate.getRecipientPhone();
    }

  public void updateFromCreateDelivery(Address addressEntity) {
      zipcode = addressEntity.getZipcode();
      address1 = addressEntity.getAddress1();
      address2 = addressEntity.getAddress2();
      recipientName = addressEntity.getRecipientName();
      recipientPhone = addressEntity.getRecipientPhone();
  }
}
