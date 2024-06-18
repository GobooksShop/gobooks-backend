package org.team.bookshop.domain.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderUpdateRequest {

  private String merchantUid;

  private OrderAddressUpdate orderAddressUpdate;

  public OrderUpdateRequest() {
  }

  public OrderUpdateRequest(String merchantUid, OrderAddressUpdate orderAddressUpdate) {
    this.merchantUid = merchantUid;
    this.orderAddressUpdate = orderAddressUpdate;
  }
}
