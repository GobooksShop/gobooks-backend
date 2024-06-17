package org.team.bookshop.domain.payment.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
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
import org.team.bookshop.domain.order.dto.OrderResponse;
import org.team.bookshop.domain.order.dto.OrderUpdateRequest;
import org.team.bookshop.domain.payment.dto.RequestCompletePayment;
import org.team.bookshop.domain.payment.dto.RequestPrevPayment;
import org.team.bookshop.domain.payment.dto.ResponsePrevPayment;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.response.ApiResponse;

@Slf4j
@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

//  @Value("${PORTONE_REST_KEY}")
  private String REST_API_KEY;

//  @Value("${PORTONE_SECRET_KEY}")
  private String REST_KEY;

  private IamportClient iamportClient;

  private final OrderService orderService;

  @PostConstruct
  public void init() {
    this.iamportClient = new IamportClient(REST_API_KEY, REST_KEY);
  }

  @PostMapping("/prepare")
  public ResponseEntity<? extends ApiResponse<?>> processPayment(
      @RequestBody @Valid RequestPrevPayment requestPrevPayment)
      throws IamportResponseException, IOException {
    boolean isEqualsPrice = orderService.validateTotalPriceByMerchantId(
        requestPrevPayment.getMerchantUid(),
        requestPrevPayment.getAmount());

    if (!isEqualsPrice) {
      log.warn("조작된 데이터이거나 주문 금액과 결제 금액이 다릅니다.");
      return ResponseEntity.badRequest()
          .body(ApiResponse.error(ErrorCode.ENTITY_NOT_FOUND, requestPrevPayment));
    }

    PrepareData prepareData = new PrepareData(requestPrevPayment.getMerchantUid(),
        new BigDecimal(requestPrevPayment.getAmount()));
    IamportResponse<Prepare> payment = iamportClient.postPrepare(prepareData);

    log.info(String.valueOf(payment.getCode()));
    //todo getCode에 따라 switch 문과 각 메서드는 service.() 특정 서비스로 변경 하는 것이 좋아보임
    //todo 분기는 successPayment(), existPreventPayment()
    if (payment.getCode() == 0) {
      log.info("결제 요청 응답. 사전 결제 등록 번호: {}, 가격: {}", prepareData.getMerchant_uid(),
          prepareData.getAmount().intValue());

      return ResponseEntity.ok(ApiResponse.success(
          ResponsePrevPayment.builder()
              .merchantUid(prepareData.getMerchant_uid())
              .amount(prepareData.getAmount().intValue())
              .build()
      ));

    } else if (payment.getCode() == 1) {
      log.error("사전 결제 응답 요청 :{} {} 가 이미 존재합니다.", payment.getCode(),
          prepareData.getMerchant_uid());
      return ResponseEntity.badRequest().body(ApiResponse.success(
          ResponsePrevPayment.builder()
              .merchantUid(prepareData.getMerchant_uid())
              .amount(prepareData.getAmount().intValue())
              .build()
      ));
    } else {
      log.error("사전 결제 요청 실패:{} {}", payment.getCode(),
          prepareData.getMerchant_uid());
      return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.PORTONE_BAD_REQUEST));
    }
  }

  @PostMapping("/completePayment")
  public ResponseEntity<? extends ApiResponse<?>> processPayment(
      @RequestBody @Valid RequestCompletePayment requestCompletePayment
  ) throws IamportResponseException, IOException {

    OrderResponse orderResponse = orderService.update(OrderUpdateRequest.builder()
        .merchantUid(requestCompletePayment.getMerchantUid())
        .orderAddressUpdate(requestCompletePayment.getOrderAddressUpdate())
        .build());

//    IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(
//        requestCompletePayment.getImpUid());
//
//    if (paymentIamportResponse.getResponse().getAmount().intValue()
//        == orderResponse.getOrderTotalAmount()) {
//      //payment db에 저장.
//    }
//    log.info("imp_uid: {} ", paymentIamportResponse.getResponse().getMerchantUid());
    return ResponseEntity.ok(ApiResponse.success());
  }
}
