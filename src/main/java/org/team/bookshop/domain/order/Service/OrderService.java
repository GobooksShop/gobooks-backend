package org.team.bookshop.domain.order.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.order.dto.OrderItemRequest;
import org.team.bookshop.domain.order.dto.OrderCreateRequest;
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
        List<OrderItemRequest> orderItemRequests = orderCreateRequest.getOrderItemRequests();
        List<OrderItem> orderItems = new ArrayList<>();

        int totalCount = 0;
        int totalPrice = 0;

        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            OrderItem orderItem = OrderItem.createOrderItem();
            Product product = productRepository.findById(orderItemRequest.getProductId()).orElseThrow(() -> new IllegalStateException("해당하는 상품이 없습니다."));
            orderItem.setProduct(product);
            orderItem.setOrderPrice(orderItemRequest.getOrderPrice());
            orderItem.setOrderCount(orderItem.getOrderCount());
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
            totalCount += orderItemRequest.getOrderCount();
            totalPrice += orderItemRequest.getOrderPrice();
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


}
