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

    // 1. user 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_USER));

    // 2. order와 user를 조인해서 order 값 가져오기.
    Order order = orderRepository.findByMerchantUid(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));

    // 2. 빈 주소 생성 -> 후에 address조회 결과가 존재하는가 여부를 판단하는 데 사용이 된다.
    Address noExistAddress = new Address();

    // 3. 입력된 user와 label에 해당하는 Address를 조회하는데, 만약 존재하지 않는다면 앞서 정의한 noExistAddress를 반환한다.
    Address address = addressRepository.findByUserAndLabel(user, label).orElse(noExistAddress);

    // 4. Delivery 생성
    Random random = new Random();
    Delivery delivery = Delivery.createDelivery(order, DeliveryStatus.READY, LocalDate.now(), Math.abs(random.nextLong()));

    // 5-1. 만약 address가 존재하지 않는다면
    // 새로운 address를 생성한다
    if (address.equals(noExistAddress)) {
      Address transferedAddress = createDeliveryRequest.toAddressEntity();
      transferedAddress.setUser(user);
      delivery.fillAddressInformation(transferedAddress);
      addressRepository.save(transferedAddress);
      // 5-2. 만약 이미 해당 address가 존재 한다면
      // 기존의 address 를 update한다.
    } else {
      address.updateFromCreateDelivery(createDeliveryRequest.toAddressEntity());
    }

  }
}

