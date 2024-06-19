package org.team.bookshop.domain.payment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.team.bookshop.domain.payment.entity.Payments;

@Getter
@Builder
public class RequestPrevPayment {

  @NotNull
  @NotEmpty
  private String merchantUid;

  @NotNull
  private int amount;

  private List<Long> productList;

  public Payments toEntity(RequestPrevPayment requestPrevPayment) {
    return Payments.builder()
        .build();
  }
}
