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
    String label = createDeliveryRequest.getOrderAddressUpdate().getLabel();

    // 1. user를 조회하기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_USER));

    // 2. 빈 주소 생성 -> 후에 address조회 결과가 존재하는가 여부를 판단하는 데 사용이 된다.
    Address noExistAddress = new Address();

    // 3. 입력된 user와 label에 해당하는 Address를 조회하는데, 만약 존재하지 않는다면 앞서 정의한 noExistAddress를 반환한다.
    Address address = addressRepository.findByUserAndLabel(user, label).orElse(noExistAddress);

    // 4. Delivery를 생성한다.
    // 4-1. 배송추적용 난수 생성
    Random random = new Random();
    long trackingNumber = random.nextLong();
    Delivery delivery = Delivery.createDelivery(DeliveryStatus.READY, LocalDate.now(), trackingNumber);

    // 5-1. 입력된 user와 label에 해당하는 address가 존재하지 않는다면 -> 새로운 address 엔티티를 생성한다.
    if (address.equals(noExistAddress)) {
      Address transferedAddress = createDeliveryRequest.toAddressEntity();
      transferedAddress.setUser(user);
      delivery.setAddress(transferedAddress);

      // 5-2. 입력된 user와 label에 해당하는 address가 존재한다면 -> 기존의 데이터를 입력된 새 주소데이터로 교체한다.
    } else {
      address.update(createDeliveryRequest.getOrderAddressUpdate());
      address.setUser(user);
      delivery.setAddress(address);
    }
    Order order = orderRepository.findByMerchantUid(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));

    order.setDelivery(delivery);


  }
}
