package org.team.bookshop.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentResponse {

  private String impUid;

  private String payMethod;

  private String buyerName;

  private String buyerEmail;

  private String paymentStatus;

  private Long amount;

  private Long orderId;

  public PaymentResponse(String impUid, String payMethod, String buyerName, String buyerEmail,
      String paymentStatus, Long amount) {
    this.impUid = impUid;
    this.payMethod = payMethod;
    this.buyerName = buyerName;
    this.buyerEmail = buyerEmail;
    this.paymentStatus = paymentStatus;
    this.amount = amount;
  }
  @Builder
  public PaymentResponse(String impUid, String payMethod, String buyerName, String buyerEmail,
      String paymentStatus, Long amount, Long orderId) {
    this.impUid = impUid;
    this.payMethod = payMethod;
    this.buyerName = buyerName;
    this.buyerEmail = buyerEmail;
    this.paymentStatus = paymentStatus;
    this.amount = amount;
    this.orderId = orderId;
  }
}
