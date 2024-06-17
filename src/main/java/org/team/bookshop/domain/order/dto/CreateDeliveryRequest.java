package org.team.bookshop.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDeliveryRequest {

  @NotBlank(message = "merchantUid 빈 값일 수 없습니다.")
  private String merchantUid;

  @NotBlank(message = "userId 빈 값일 수 없습니다.")
  private Long userId;

  private OrderAddressUpdate orderAddressUpdate;

  public CreateDeliveryRequest(String merchantUid, Long userId,
      OrderAddressUpdate orderAddressUpdate) {
    this.merchantUid = merchantUid;
    this.userId = userId;
    this.orderAddressUpdate = orderAddressUpdate;
  }

  public CreateDeliveryRequest() {
  }
}
