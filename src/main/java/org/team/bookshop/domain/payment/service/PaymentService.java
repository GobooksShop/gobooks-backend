package org.team.bookshop.domain.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.dto.response.OrderItemResponse;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.payment.dto.PaymentResponse;
import org.team.bookshop.domain.payment.dto.RequestCompletePayment;
import org.team.bookshop.domain.payment.dto.RequestPrevPayment;
import org.team.bookshop.domain.payment.dto.ResponsePrevPayment;
import org.team.bookshop.domain.payment.entity.Payments;
import org.team.bookshop.domain.payment.enums.PaymentStatus;
import org.team.bookshop.domain.payment.repository.PaymentRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;
import org.team.bookshop.global.response.ApiResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;

  @Value("${PORTONE_REST_KEY}")
  private String REST_API_KEY;

  @Value("${PORTONE_SECRET_KEY}")
  private String REST_KEY;

  private IamportClient iamportClient;

  private final OrderService orderService;

  @PostConstruct
  public void init() {
    this.iamportClient = new IamportClient(REST_API_KEY, REST_KEY);
  }

  @Transactional
  public ResponsePrevPayment preparePayment(RequestPrevPayment requestPrevPayment) {

    boolean isEqualsPrice = orderService.validateTotalPriceByMerchantId(
        requestPrevPayment.getMerchantUid(), requestPrevPayment.getAmount());

    if (!isEqualsPrice) {
      log.warn("조작된 데이터이거나 주문 금액과 결제 금액이 다릅니다.");
      throw new ApiException(ErrorCode.ENTITY_NOT_FOUND);
    }

    PrepareData prepareData = new PrepareData(requestPrevPayment.getMerchantUid(),
        new BigDecimal(requestPrevPayment.getAmount()));
    IamportResponse<Prepare> payment = postPreparePayment(prepareData);
    log.info(String.valueOf(payment.getCode()));
    if (payment.getCode() == 0) {
      log.info("결제 요청 응답. 사전 결제 등록 번호: {}, 가격: {}", prepareData.getMerchant_uid(),
          prepareData.getAmount().intValue());
    } else if (payment.getCode() == 1) {
      log.warn("사전 결제 응답 요청 :{} {} 가 이미 존재합니다.", payment.getCode(), prepareData.getMerchant_uid());
    } else {
      log.error("사전 결제 요청 실패:{} {}", payment.getCode(), prepareData.getMerchant_uid());
      throw new ApiException(ErrorCode.PORTONE_BAD_REQUEST);
    }

    return ResponsePrevPayment.builder()
        .merchantUid(prepareData.getMerchant_uid())
        .amount(prepareData.getAmount().intValue())
        .build();
  }


  @Transactional
  public PaymentResponse complatePayment(RequestCompletePayment requestCompletePayment) {
    Order order = orderService.findByMerchantUid(requestCompletePayment.getMerchantUid());

    IamportResponse<Payment> paymentIamportResponse = paymentByImpUid(
        requestCompletePayment.getImpUid());
    Payment payment = paymentIamportResponse.getResponse();
    int payment_totalPrice = payment.getAmount().intValue();
    if (payment_totalPrice == order.getOrderTotalPrice()) {
      //payment db에 저장.
      log.info("===================================진입==================================");
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

      // 해당 주문의 status를 payed로 변경하는 부분
      Order orderFoundByMerchantUid = orderRepository.findByMerchantUid(requestCompletePayment.getMerchantUid())
          .orElseThrow(() -> new ApiException(
              ErrorCode.NO_EXISTING_ORDER));
      orderFoundByMerchantUid.changeOrderStatus(OrderStatus.PAYED);
      paymentRepository.save(paymentEntity);
      System.out.println("페이먼트Repository에 저장 됐습니다.");
    } else {
      try {
        CancelData cancelData = new CancelData(requestCompletePayment.getImpUid(), true);
        iamportClient.cancelPaymentByImpUid(cancelData);
      } catch (IamportResponseException e) {
        log.error("존재하지 않거나 삭제된 거래 번호 입니다. 거래번호 : {}", requestCompletePayment.getImpUid());
        throw new ApiException(ErrorCode.PORTONE_BAD_REQUEST);
      } catch (IOException e){
        log.error("PortOne Api 접근에 실패했습니다. \n {}", e.getMessage());
        throw new ApiException(ErrorCode.PORTONE_BAD_REQUEST);
      }
    }

    return PaymentResponse.builder()
        .orderId(order.getId())
        .paymentStatus(PaymentStatus.COMPlETE.name())
        .amount(payment.getAmount().longValue())
        .buyerEmail(payment.getBuyerEmail())
        .build();
  }

  private IamportResponse<Prepare> postPreparePayment(PrepareData prepareData) {
    try {
      return iamportClient.postPrepare(prepareData);
    } catch (IamportResponseException | IOException e) {
      log.error(e.getMessage());
      throw new ApiException(ErrorCode.PORTONE_BAD_REQUEST);
    }
  }

  private IamportResponse<Payment> paymentByImpUid(String impUid){
    try {
      return iamportClient.paymentByImpUid(impUid);
    }catch (IamportResponseException | IOException e) {
      log.error(e.getMessage());
      throw new ApiException(ErrorCode.PORTONE_BAD_REQUEST);
    }
  }

}
