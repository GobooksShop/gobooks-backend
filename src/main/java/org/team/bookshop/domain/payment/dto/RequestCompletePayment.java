package org.team.bookshop.domain.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestCompletePayment {

  @NotNull
  @NotBlank
  private String impUid;

  @NotNull
  @NotBlank
  private String merchantUid;


}
