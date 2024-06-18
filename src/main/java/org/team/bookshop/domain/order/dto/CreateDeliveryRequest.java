package org.team.bookshop.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.team.bookshop.domain.user.entity.Address;

@Data
@Builder
public class CreateDeliveryRequest {

  @NotBlank(message = "merchantUid 빈 값일 수 없습니다.")
  private String merchantUid;

  @NotBlank(message = "userId 빈 값일 수 없습니다.")
  private Long userId;

  private OrderAddressUpdate orderAddressUpdate;

  public Address toAddressEntity() {
    return orderAddressUpdate.toEntity();
  }

}
