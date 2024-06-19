package org.team.bookshop.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.payment.dto.PaymentResponse;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

  @Id
  @Column(name = "imp_uid")
  private String impUid;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;
  /**
   * 결제 방식
   */
  private String payMethod;
  /**
   * 구매 채널
   */
  private String channel;
  /**
   * pg 제공사
   */
  private String pgProvider;
  /**
   * 구매자 이름
   */
  private String buyerName;
  /**
   * 구매자 이메일
   */
  private String buyerEmail;
  /**
   * 구매자 전화번호
   */
  private String buyerTel;
  /**
   * 구매자 주소
   */
  private String buyerAddr;
  /**
   * 구매자 우편번호
   */
  private String buyerPostcode;
  /**
   * 결제 상태.
   */
  private String paymentStatus;
  /**
   * 총 가격 금액
   */
  private Long amount;

  public PaymentResponse toPaymentResponse() {
    return new PaymentResponse(impUid, payMethod, buyerName, buyerEmail, paymentStatus, amount);
  }

}
