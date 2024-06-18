package org.team.bookshop.domain.order.Service;

import java.time.LocalDate;
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

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_USER));

    Address noExistAddress = new Address();

    Address address = addressRepository.findByUserAndLabel(user, label).orElse(noExistAddress);
    Delivery delivery = Delivery.createDelivery(DeliveryStatus.READY, LocalDate.now(), 1234L);
    // 해당 user의 특정 label에 해당하는 address가 존재하지 않는다면
    if (address.equals(noExistAddress)) {
      Address transferedAddress = createDeliveryRequest.toAddressEntity();
      transferedAddress.setUser(user);
      delivery.setAddress(transferedAddress);
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