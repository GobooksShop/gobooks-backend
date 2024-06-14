package org.team.bookshop.domain.payment.dto;

import lombok.Builder;

@Builder
public class ResponsePrevPayment {

  private String merchantUid;

  private int amount;


  public String getMerchantUid() {
    return merchantUid;
  }

  public int getAmount() {
    return amount;
  }

}
