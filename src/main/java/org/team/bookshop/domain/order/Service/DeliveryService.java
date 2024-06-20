package org.team.bookshop.domain.order.Service;

import java.time.LocalDate;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.dto.CreateDeliveryRequest;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.enums.DeliveryStatus;
import org.team.bookshop.domain.order.repository.DeliveryRepository;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.AddressRepository;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final OrderRepository orderRepository;
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;

  @Transactional
  public void createDelivery(CreateDeliveryRequest createDeliveryRequest) {
    Long userId = createDeliveryRequest.getUserId();
    String merchantUid = createDeliveryRequest.getMerchantUid();

    // 1. user 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_USER));

    // 2. order와 user를 조인해서 order 값 가져오기.
    Order order = orderRepository.findByMerchantUidWithDelivery(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));

    // 3-1. 만약 해당 주문에 대한 delivery가 존재하지 않는다면 새 delivery를 만든다.
    if (order.getDelivery() == null) {
      Random random = new Random();
      Delivery delivery = Delivery.createDelivery(order, DeliveryStatus.READY, LocalDate.now(), Math.abs(random.nextLong()));
      delivery.fillAddressInformation(createDeliveryRequest.getOrderAddressUpdate().toEntity());
      deliveryRepository.save(delivery);
    } // 3-2. 만약 해당 주문에 대한 delivery가 존재한다면 주소정보만을 변경한다.
    else {
      order.getDelivery().fillAddressInformation(createDeliveryRequest.getOrderAddressUpdate().toEntity());
    }


  }
}

