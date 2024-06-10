package org.team.bookshop.domain.order.Service;

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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;


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

        // 주문 생성 요청을 바탕으로 OrderItems 엔티티를 만드는 과정
        List<OrderItem> orderItems = orderCreateRequest.toOrderItems();
        List<Long> productIds = orderCreateRequest.toProductIds();

        int totalCount = 0;
        int totalPrice = 0;

        for (int i = 0; i < orderItems.size(); i++) {
            Long productId = productIds.get(i);
            OrderItem orderItem = orderItems.get(i);

            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("해당하는 상품이 존재하지 않습니다"));
            product.decreaseStock(orderItem.getOrderCount()); // 주문 상품의 재고 감소

            orderItem.setProduct(product);
            orderItem.setOrder(order);

            totalCount += orderItem.getOrderCount();
            totalPrice += orderItem.getOrderPrice();

            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }

        // 주문 생성 요청을 바탕으로 Delivery, Address 엔티티를 만드는 과정
        Delivery delivery = orderCreateRequest.toDeliveryEntity();
        Address address = orderCreateRequest.toAddressEntity();
        delivery.setAddress(address);
        deliveryRepository.save(delivery);

        order.setDelivery(delivery);

        order.setOrderTotalAmount(totalCount);
        order.setOrderTotalPrice(totalPrice);

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

        Address address = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당하는 주문이 존재하지 않습니다")).getDelivery().getAddress();

        // 주문 수정 요청을 바탕으로 기존의 주소를 수정
        address.setZipcode(orderAddressUpdate.getZipcode());
        address.setAddress1(orderAddressUpdate.getAddress1());
        address.setAddress2(orderAddressUpdate.getAddress2());
        address.setRecipientName(orderAddressUpdate.getRecipientName());
        address.setRecipientPhone(orderAddressUpdate.getRecipientPhone());

        return orderId;
    }

    @Transactional
    public Long delete(OrderDeleteRequest orderDeleteRequest) {
        Long orderId = orderDeleteRequest.getOrderId();

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

}
