package org.team.bookshop.domain.order.Service;

import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void save(OrderCreateRequest orderCreateRequest) {
        Order order = Order.createOrder();

        // User
        // 후에 cookie를 통해 userID를 받아오는 부분을 추가해야 함
        User user = userRepository.findById(1L).orElseThrow(() -> new IllegalStateException("해당하는 회원이 없습니다."));

        // orderItems
        List<OrderItem> orderItems = orderCreateRequest.toOrderItems();
        List<Long> productIds = orderCreateRequest.toProductIds();

        int totalCount = 0;
        int totalPrice = 0;

        for (int i = 0; i < orderItems.size(); i++) {
            Long productId = productIds.get(i);
            OrderItem orderItem = orderItems.get(i);

            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("해당하는 상품이 존재하지 않습니다"));
            product.decreaseStock(orderItem.getOrderCount());
            orderItem.setProduct(product);
            orderItem.setOrder(order);

            totalCount += orderItem.getOrderCount();
            totalPrice += orderItem.getOrderPrice();

            orderItemRepository.save(orderItem);
        }

        // Delivery
        Delivery delivery = orderCreateRequest.toDeliveryEntity();
        deliveryRepository.save(delivery);

        // Address
        Address address = orderCreateRequest.toAddressEntity();
        address.setDelivery(delivery);
        addressRepository.save(address);

        order.setOrderTotalAmount(totalCount);
        order.setOrderTotalPrice(totalPrice);

        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setUser(user);

        orderRepository.save(order);
    }

    @Transactional
    public void update(OrderUpdateRequest orderUpdateRequest) {
        Long orderId = orderUpdateRequest.getOrderId();
        OrderAddressUpdate orderAddressUpdate = orderUpdateRequest.getOrderAddressUpdate();

        Delivery delivery = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당하는 주문이 존재하지 않습니다")).getDelivery();

        Address address = addressRepository.findByDelivery(delivery);

        address.setZipcode(orderAddressUpdate.getZipcode());
        address.setAddress1(orderAddressUpdate.getAddress1());
        address.setAddress2(orderAddressUpdate.getAddress2());
        address.setRecipientName(orderAddressUpdate.getRecipientName());
        address.setRecipientPhone(orderAddressUpdate.getRecipientPhone());
    }

    @Transactional
    public void delete(OrderDeleteRequest orderDeleteRequest) {
        Long orderId = orderDeleteRequest.getOrderId();
        // 주문 취소 시 관련 주문 수량 원상복귀
        orderRepository.deleteById(orderId);
    }

}
