package org.team.bookshop.domain.payment.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.payment.dto.RequestPrevPayment;
import org.team.bookshop.domain.payment.dto.ResponsePrevPayment;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.response.ApiResponse;

@Slf4j
@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

  //@Value("${PORTONE_REST_KEY}")
  private String REST_API_KEY;

  //@Value("${PORTONE_SECRET_KEY}")
  private String REST_KEY;

  private IamportClient iamportClient;

  private final OrderService orderService;

  @PostConstruct
  public void init() {
    this.iamportClient = new IamportClient(REST_API_KEY, REST_KEY);
  }

  @PostMapping("/prepare")
  public ResponseEntity<? extends ApiResponse<?>> processPayment(
      HttpSession httpSession,
      @RequestBody RequestPrevPayment requestPrevPayment)
      throws IamportResponseException, IOException {
    //todo Order쪽에 데이터가 save 되는 작업을 진행해야 합니다. validation
    PrepareData prepareData = new PrepareData(requestPrevPayment.getMerchantUid(),
        new BigDecimal(requestPrevPayment.getAmount()));
    IamportResponse<Prepare> payment = iamportClient.postPrepare(prepareData);

    log.info("결제 요청 응답. 사전 결제 등록 번호: {}, 가격: {}", prepareData.getMerchant_uid(),
        prepareData.getAmount().intValue());

    if (payment.getCode() == 0) {
      httpSession.setAttribute("merchant_uid", requestPrevPayment.getMerchantUid());
      httpSession.setAttribute("amount", prepareData.getAmount().intValue());

      return ResponseEntity.ok(ApiResponse.success(
          ResponsePrevPayment.builder()
              .merchantUid(prepareData.getMerchant_uid())
              .amount(prepareData.getAmount().intValue())
              .build()
      ));

    } else {
      log.error("사전 결제 응답 요청 실패:{} {} 가 이미 존재합니다.", payment.getCode(),
          prepareData.getMerchant_uid());
      return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.PORTONE_BAD_REQUEST));
    }
  }

  @PostMapping("/completePayment")
  public ResponseEntity<? extends ApiResponse<?>> processPayment(
      @RequestBody Map<String, String> map
  ) throws IamportResponseException, IOException {
    for (Entry<String, String> entry : map.entrySet()) {
      log.info("Key: {}, value: {}", entry.getKey(), entry.getValue());
    }

    IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(
        map.get("imp_uid"));

    log.info("imp_uid: {} ", paymentIamportResponse.getResponse().getMerchantUid());
    return ResponseEntity.ok(ApiResponse.success());
  }
}
