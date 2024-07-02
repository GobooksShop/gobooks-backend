package org.team.bookshop.domain.payment.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.payment.dto.RequestCompletePayment;
import org.team.bookshop.domain.payment.dto.RequestPrevPayment;
import org.team.bookshop.domain.payment.dto.ResponsePrevPayment;
import org.team.bookshop.domain.payment.repository.PaymentRepository;
import org.team.bookshop.domain.payment.service.PaymentService;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.response.ApiResponse;

@Slf4j
@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/prepare")
  public ResponseEntity<? extends ApiResponse<?>> processPayment(
      @RequestBody @Valid RequestPrevPayment requestPrevPayment) {

    ResponsePrevPayment responsePrevPayment = paymentService.preparePayment(requestPrevPayment);

    return ResponseEntity.ok(ApiResponse.success(
        ResponsePrevPayment.builder()
            .merchantUid(responsePrevPayment.getMerchantUid())
            .amount(responsePrevPayment.getAmount())
            .build()));
  }

  @PostMapping("/completePayment")
  public ResponseEntity<? extends ApiResponse<?>> processPayment(
      @RequestBody @Valid RequestCompletePayment requestCompletePayment) {

    paymentService.complatePayment(requestCompletePayment);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
