package org.team.bookshop.domain.payment.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.team.bookshop.domain.order.entity.Order;

public class PaymentResponse {

  private String impUid;

  private String payMethod;

  private String buyerName;

  private String buyerEmail;

  private String paymentStatus;

  private Long amount;

  public PaymentResponse(String impUid, String payMethod, String buyerName, String buyerEmail,
      String paymentStatus, Long amount) {
    this.impUid = impUid;
    this.payMethod = payMethod;
    this.buyerName = buyerName;
    this.buyerEmail = buyerEmail;
    this.paymentStatus = paymentStatus;
    this.amount = amount;
  }
}
