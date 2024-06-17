package org.team.bookshop.domain.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.team.bookshop.domain.order.dto.OrderAddressUpdate;

@Getter
public class RequestCompletePayment {

  @NotNull
  @NotBlank
  private String impUid;

  @NotNull
  @NotBlank
  private String merchantUid;

  private OrderAddressUpdate orderAddressUpdate;

}
