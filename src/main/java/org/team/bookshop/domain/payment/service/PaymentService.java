package org.team.bookshop.domain.payment.service;

import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.dto.OrderItemResponse;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.payment.entity.Payments;
import org.team.bookshop.domain.payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;

  @Transactional
  public OrderItemResponse complatePayment(Payment payment, Order order) {
    Payments paymentEntity = Payments.builder()
        .impUid(payment.getImpUid())
        .paymentStatus(payment.getStatus())
        .order(order)
        .payMethod(payment.getPayMethod())
        .buyerEmail(payment.getBuyerEmail())
        .channel(payment.getChannel())
        .pgProvider(payment.getPgProvider())
        .buyerName(payment.getBuyerName())
        .buyerEmail(payment.getBuyerEmail())
        .buyerTel(payment.getBuyerTel())
        .buyerAddr(payment.getBuyerAddr())
        .buyerPostcode(payment.getBuyerPostcode())
        .amount(payment.getAmount().longValue())
        .build();
    paymentRepository.save(paymentEntity);
    return null;
  }


}
