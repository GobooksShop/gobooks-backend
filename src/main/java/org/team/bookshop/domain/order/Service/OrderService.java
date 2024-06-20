package org.team.bookshop.domain.order.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.team.bookshop.domain.order.dto.OrderAbstractResponse;
import org.team.bookshop.domain.order.dto.OrderAddressCreate;
import org.team.bookshop.domain.order.dto.OrderAddressUpdate;
import org.team.bookshop.domain.order.dto.OrderCreateRequest;
import org.team.bookshop.domain.order.dto.OrderItemRequest;
import org.team.bookshop.domain.order.dto.OrderListResponse;
import org.team.bookshop.domain.order.dto.OrderResponse;
import org.team.bookshop.domain.order.dto.OrderUpdateRequest;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.entity.OrderItem;
import org.team.bookshop.domain.order.enums.DeliveryStatus;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.order.repository.DeliveryRepository;
import org.team.bookshop.domain.order.repository.OrderItemRepository;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.payment.entity.Payments;
import org.team.bookshop.domain.payment.repository.PaymentRepository;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final DeliveryRepository deliveryRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final PaymentRepository paymentRepository;


  public Order findById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_BOOK));
  }

  @Transactional
  public Long save(OrderCreateRequest orderCreateRequest) {

    // 입력된 merchantId에 해당하는 order가 이미 존재한다면, 그 order의 id를 반환한다.
    Order orderFoundByMerchantUid = orderRepository.findByMerchantUid(
            orderCreateRequest.getMerchantUid())
        .orElseGet(
            Order::notExistingOrder);

    if (!orderFoundByMerchantUid.getMerchantUid().equals("xxx")) {
      return orderFoundByMerchantUid.getId();
    }

    // 주문 생성 요청을 바탕으로 Order 엔티티를 만드는 과정
    Order order = Order.createOrder();

    // User
    User user = userRepository.findById(orderCreateRequest.getUserId())
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_USER));

    order.setUser(user);

    List<OrderItemRequest> orderItemRequests = orderCreateRequest.getOrderItemRequests()
        .stream().sorted(Comparator.comparing(OrderItemRequest::getProductId))
        .toList();

    // 주문한 상품의 id 목록
    List<Long> productIds = orderCreateRequest.toProductIds();

    // 해당하는 id 상품 조회
    List<Product> products = productRepository.findByProductIds(productIds);

    //// 검증과정
    // 0. 올바른 productId들이 입력되었는 지 여부 검증
    if (products.size() != orderItemRequests.size()) {
      throw new ApiException(ErrorCode.NO_EXISTING_BOOK);
    }

    for (int i = 0; i < products.size(); i++) {
      // 1. 상품 가격이 올바른지 검증하기
      if (orderItemRequests.get(i).getPrice() != products.get(i).getFixedPrice()) {
        throw new ApiException(ErrorCode.INVALID_PRODUCT_PRICE_INFO);
      }

      // 2. 넘겨진 productId가 실제 존재하는 지 검증하기
      if (!orderItemRequests.get(i).getProductId().equals(products.get(i).getId())) {
        throw new ApiException(ErrorCode.NO_EXISTING_BOOK);
      }

      // 3. 재고 부족 여부 검증하기
      if (orderItemRequests.get(i).getOrderCount() > products.get(i).getStockQuantity()) {
        throw new ApiException(ErrorCode.NOT_ENOUGH_STOCK_QUANTITY);
      }
    }

    // 주문 생성 요청을 바탕으로 OrderItems 엔티티를 만드는 과정
    List<OrderItem> orderItems = orderCreateRequest.toOrderItems();

    int totalCount = 0;
    int totalPrice = 0;

    for (int i = 0; i < orderItems.size(); i++) {
      Long productId = productIds.get(i);
      OrderItem orderItem = orderItems.get(i);

      Product product = productRepository.findById(productId)
          .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_BOOK));
      product.decreaseStock(orderItem.getOrderCount()); // 주문 상품의 재고 감소

      orderItem.setProduct(product);
      orderItem.setOrder(order);

      totalCount += orderItem.getOrderCount();
      totalPrice += orderItem.getOrderPrice() * orderItem.getOrderCount();

      orderItemRepository.save(orderItem);
      order.getOrderItems().add(orderItem);
    }

    order.setOrderTotalAmount(totalCount);
    order.setOrderTotalPrice(totalPrice);

    // 4. merchantUid가 존재 하지 않는다면 결제용 주문 번호 생성.
    if (!StringUtils.hasText(orderCreateRequest.getMerchantUid())) {
      order.setMerchantUid("gbs" + UUID.randomUUID());
    }

    order.setOrderStatus(OrderStatus.ACCEPTED);
    order.setOrderDateTime(LocalDateTime.now());
//        order.setUser(user);

    orderRepository.save(order);
    return order.getId();
  }

  // 주문 수정기능
//  @Transactional
//  public OrderResponse update(OrderUpdateRequest orderUpdateRequest) {
//    String merchantUid = orderUpdateRequest.getMerchantUid();
//
//    OrderAddressUpdate orderAddressUpdate = orderUpdateRequest.getOrderAddressUpdate();
//
//    Order order = orderRepository.findByMerchantUid(merchantUid)
//        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));
//    Delivery delivery = order.getDelivery();
//    Address address = delivery.getAddress();
//
//    // 주문 수정 요청을 바탕으로 기존의 주소를 수정
//    address.setZipcode(orderAddressUpdate.getZipcode());
//    address.setAddress1(orderAddressUpdate.getAddress1());
//    address.setAddress2(orderAddressUpdate.getAddress2());
//    address.setRecipientName(orderAddressUpdate.getRecipientName());
//    address.setRecipientPhone(orderAddressUpdate.getRecipientPhone());
//    return OrderResponse.builder()
//        .orderId(order.getId())
//        .orderStatus(OrderStatus.PAYED)
//        .merchantUid(order.getMerchantUid())
//        .orderTotalAmount(order.getOrderTotalPrice())
//        .orderTotalPrice(order.getOrderTotalPrice())
//        .build();
//  }

  @Transactional
  public Long delete(Long orderId) {
    Order order = orderRepository.findWithAllRelatedEntityById(orderId);

    // 해당 주문 내에 포함된 상품의 재고를 다시 원상복구 한다.
    List<OrderItem> orderItems = order.getOrderItems();
    for (OrderItem orderItem : orderItems) {
      // 재고수량 회복
      orderItem.getProduct().increaseStock(orderItem.getOrderCount());

      // orderItem 삭제
      orderItemRepository.delete(orderItem);
    }

    // 관련된 엔티티인 delivery, address, orderItem모두 삭제
    Delivery delivery = order.getDelivery();
    deliveryRepository.delete(delivery);
    orderRepository.deleteById(orderId);

    // 삭제된 orderId반환
    return orderId;
  }

  public Order findByIdForCreateResponse(long orderId) {
    return orderRepository.findWithOrderItems(orderId);
  }

//  @Transactional
//  public void setOrderAddress(Long orderId, OrderAddressCreate orderAddressCreate) {
//    Order order = orderRepository.findById(orderId)
//        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));
//
//    Address address = orderAddressCreate.toEntity();
//    Delivery delivery = Delivery.createDelivery(DeliveryStatus.READY, LocalDate.now(), 1L);
//    delivery.setAddress(address);
//
//    deliveryRepository.save(delivery);
//
//    order.setDelivery(delivery);
//
//  }

  // orderListResponse용 : userID 이용 하여 orderListResponse용 dto를 반환하는 기능
  public OrderListResponse findByUserIdForOrderListResponse(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_USER));
    List<Order> orders = orderRepository.findOrdersByUser(user);
    List<OrderAbstractResponse> orderAbstractResponses = orders.stream().map(
            Order::toOrderAbstractResponse)
        .toList();

    return new OrderListResponse(orderAbstractResponses);

  }


  //// 해당 orderId를 가지고 있는 주문의 총 주문가격이, totalPrice와 동일한 지 비교하는 기능
  public boolean validateTotalPriceByOrderId(Long orderId, int totalPrice) {

    // 1. orderId를 통해 Order를 조회한다.
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));

    // 2. 해당 주문의 총 가격과, 인자로 전달된 가격이 동일한 지 비교한 후, 그 결과를 true or false로 반환한다.
    return order.getOrderTotalPrice() == totalPrice;
  }


  //// 해당 merchantUid를 가지고 있는 주문의 총 주문가격이, totalPrice와 동일한 지 비교하는 기능
  public boolean validateTotalPriceByMerchantId(String merchantUid, int totalPrice) {

    // 1. merchantUid를 통해 Order를 조회한다.
    Order order = orderRepository.findByMerchantUid(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));

    // 2. 해당 주문의 총 가격과, 인자로 전달된 가격이 동일한 지 비교한 후, 그 결과를 true or false로 반환한다.
    return order.getOrderTotalPrice() == totalPrice;
  }

  //// 주문을 상세 조회하기 위한 DTO를 반환하는 기능
  public OrderResponse getOrderDetail(String merchantUid) {

    // 1. 주문 조회하기

    Order order = orderRepository.findByMerchantUid(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));

    // 2. 결제 조회하기 by Order

    Payments payment = paymentRepository.findPaymentByOrder(order)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_PAYMENT_INFO_WITH_ORDER));

    // 3. 조회한 주문과 결제정보를 바탕으로, 주문 상세조회용 OrderResponse만들기

    OrderResponse orderResponse = new OrderResponse(
        order.getId(),
        order.getMerchantUid(),
        order.getOrderItems().stream().map(OrderItem::toOrderItemResponse)
            .collect(Collectors.toList()),
        order.getOrderStatus(),
        order.getDelivery().toOrderDeliveryResponse(),
        order.getOrderTotalPrice(),
        order.getOrderTotalAmount(),
        payment.toPaymentResponse()
    );

    // 4. 반환

    return orderResponse;
  }

  public Order findByMerchantUid(String merchantUid) {
    return orderRepository.findByMerchantUid(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));
  }

  public int getOrderTotalPriceByMerchantUid(String merchantUid) {
    return orderRepository.findByMerchantUid(merchantUid)
        .orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER)).getOrderTotalPrice();
  }
}
