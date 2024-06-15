package org.team.bookshop.domain.payment.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.team.bookshop.domain.payment.entity.Payment;
import org.team.bookshop.domain.payment.enums.PaymentStatus;

@Getter
@Builder
public class RequestPrevPayment {

  private String merchantUid;

  private int amount;

  private List<Long> productList;

  public Payment toEntity(RequestPrevPayment requestPrevPayment) {
    return Payment.builder()
        .price(requestPrevPayment.getAmount())
        .paymentStatus(PaymentStatus.READY)
        .build();
  }
}