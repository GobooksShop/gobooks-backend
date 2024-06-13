package org.team.bookshop.domain.order.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.dto.*;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.order.entity.OrderItem;
import org.team.bookshop.domain.order.enums.DeliveryStatus;
import org.team.bookshop.domain.order.enums.OrderStatus;
import org.team.bookshop.domain.order.repository.DeliveryRepository;
import org.team.bookshop.domain.order.repository.OrderItemRepository;
import org.team.bookshop.domain.order.repository.OrderRepository;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.AddressRepository;
import org.team.bookshop.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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


    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당하는 회원이 없습니다."));
    }

    @Transactional
    public Long save(OrderCreateRequest orderCreateRequest) {
        // 주문 생성 요청을 바탕으로 Order 엔티티를 만드는 과정
        Order order = Order.createOrder();

        // User
        // 후에 cookie를 통해 userID를 받아오는 부분을 추가해야 함
        //User user = userRepository.findById(1L).orElseThrow(() -> new IllegalStateException("해당하는 회원이 없습니다."));


        List<OrderItemRequest> orderItemRequests = orderCreateRequest.getOrderItemRequests()
            .stream().sorted(Comparator.comparing(OrderItemRequest::getProductId))
            .toList();

        // 주문한 상품의 id 목록
        List<Long> productIds = orderCreateRequest.toProductIds();

        // 해당하는 id 상품 조회
        List<Product> products = productRepository.findByProductIds(productIds);

        //// 검증과정
        for(int i=0; i<products.size(); i++) {
            // 1. 상품 가격이 올바른지 검증하기
            if(orderItemRequests.get(i).getPrice() != products.get(i).getFixedPrice())
                throw new ApiException(ErrorCode.INVALID_PRODUCT_PRICE_INFO);

            // 2. orderId기준으로 검증하기
            if(!orderItemRequests.get(i).getProductId().equals(products.get(i).getId()))
                throw new ApiException(ErrorCode.NO_EXISTING_BOOK);

            // 3. 재고 부족 여부 검증하기
            if(orderItemRequests.get(i).getOrderCount() > products.get(i).getStockQuantity())
                throw new ApiException(ErrorCode.NOT_ENOUGH_STOCK_QUANTITY);
        }



        // 주문 생성 요청을 바탕으로 OrderItems 엔티티를 만드는 과정
        List<OrderItem> orderItems = orderCreateRequest.toOrderItems();



        int totalCount = 0;
        int totalPrice = 0;

        for (int i = 0; i < orderItems.size(); i++) {
            Long productId = productIds.get(i);
            OrderItem orderItem = orderItems.get(i);

            Product product = productRepository.findById(productId).orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_BOOK));
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

        order.setMerchantId(UUID.randomUUID().toString());

        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setOrderDateTime(LocalDateTime.now());
//        order.setUser(user);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public Long update(OrderUpdateRequest orderUpdateRequest) {
        Long orderId = orderUpdateRequest.getOrderId();

        OrderAddressUpdate orderAddressUpdate = orderUpdateRequest.getOrderAddressUpdate();

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ApiException(ErrorCode.NO_EXISTING_ORDER));
        Delivery delivery = order.getDelivery();
        Address address = delivery.getAddress();

        // 주문 수정 요청을 바탕으로 기존의 주소를 수정
        address.setZipcode(orderAddressUpdate.getZipcode());
        address.setAddress1(orderAddressUpdate.getAddress1());
        address.setAddress2(orderAddressUpdate.getAddress2());
        address.setRecipientName(orderAddressUpdate.getRecipientName());
        address.setRecipientPhone(orderAddressUpdate.getRecipientPhone());

        return orderId;
    }

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

    @Transactional
    public void setOrderAddress(Long orderId, OrderAddressCreate orderAddressCreate) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당하는 주문이 존재하지 않습니다"));

        Address address = orderAddressCreate.toEntity();
        Delivery delivery = Delivery.createDelivery(DeliveryStatus.READY, LocalDate.now(), 1L);
        address.setDelivery(delivery);
        delivery.setAddress(address);
        
        deliveryRepository.save(delivery);

        order.setDelivery(delivery);

    }

    public boolean validateTotalPriceByOrderId(Long orderId, int totalPrice) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalStateException("해당하는 주문이 존재하지 않습니다"));

        return order.getOrderTotalPrice() == totalPrice;
    }

    public boolean validateTotalPriceByMerchantId(String merchantId, int totalPrice) {
        Order order = orderRepository.findByMerchantId(merchantId)
            .orElseThrow(() -> new IllegalStateException("해당하는 주문이 존재하지 않습니다"));

        return order.getOrderTotalPrice() == totalPrice;
    }
}
