package org.team.bookshop.domain.order.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.BookshopApplication;
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
import org.team.bookshop.domain.user.repository.AddressRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = BookshopApplication.class)
class OrderServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    @Transactional
    @Rollback(value = false)
    void beforeEach() {
        Product product1 = new Product("제목1", "작가1", "isbn1", "책1 내용입니다", 20000, LocalDate.ofYearDay(2020, 15), Product.Status.AVAILABLE, 20);
        Product product2 = new Product("제목2", "작가2", "isbn2", "책2 내용입니다", 30000, LocalDate.ofYearDay(2024, 30), Product.Status.AVAILABLE, 30);

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 주문생성테스트1() {
        // 주문 시 데이터베이스에 Order, OrderItem, Delivery, Address가 적절히 반영되어야 한다.
        OrderItemRequest orderItemRequest1 = new OrderItemRequest(1L, 2, 18000);
        OrderItemRequest orderItemRequest2 = new OrderItemRequest(2L, 3, 27000);
        OrderAddressCreate orderAddressCreate = new OrderAddressCreate("56442", "서울특별시 용산구 한강로 11", "62-34", "홍길동", "010-1234-5678");

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(orderItemRequest1, orderItemRequest2), orderAddressCreate);
        // 주문 완료
        orderService.save(orderCreateRequest);

        // 1. 주문이 잘 생성되었는가.
        List<Order> allOrders = orderRepository.findAll();
        assertThat(allOrders.size()).isEqualTo(1);
        assertThat(allOrders.get(0).getId()).isEqualTo(1L);
        assertThat(allOrders.get(0).getOrderTotalPrice()).isEqualTo(45000);
        assertThat(allOrders.get(0).getOrderTotalAmount()).isEqualTo(5);
        assertThat(allOrders.get(0).getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);

        // 2. 해당 주문의 orderItems 내용 확인
        Order order = orderRepository.findAll().get(0);
        List<OrderItem> orderItems = order.getOrderItems();

        assertThat(orderItems.size()).isEqualTo(2);
        OrderItem orderItem1 = orderItems.get(0);
        assertThat(orderItem1.getProduct().getTitle()).isEqualTo("제목1");

        // 3. 해당 주문의 Delivery 내용 확인
        Delivery delivery = order.getDelivery();

        assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(delivery.getTrackingNumber()).isEqualTo(1L);


        // 4. 해당 주문의 address 내용 확인하기
        Address address = delivery.getAddress();
        assertThat(address.getZipcode()).isEqualTo("56442");
    }


    @Test
    @Rollback(value = false)
    void 주문생성테스트2() throws InterruptedException {

        OrderItemRequest orderItemRequest1 = new OrderItemRequest(1L, 2, 18000);
        OrderItemRequest orderItemRequest2 = new OrderItemRequest(2L, 3, 27000);
        OrderAddressCreate orderAddressCreate = new OrderAddressCreate("56442", "서울특별시 용산구 한강로 11", "62-34", "홍길동", "010-1234-5678");

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(orderItemRequest1, orderItemRequest2), orderAddressCreate);

        // 주문 완료
        orderService.save(orderCreateRequest);

        Product product1 = productRepository.findById(1L).get();
        Product product2 = productRepository.findById(2L).get();

        // 1. 주문을 완료하면 해당 상품의 재고가 주문 양만큼 감소하여야 한다.
        assertThat(product1.getStockQuantity()).isEqualTo(18);
        assertThat(product2.getStockQuantity()).isEqualTo(27);

        // 2. 상품의 재고 이상으로 주문하게 되면 예외가 발생하여야 한다.
        assertThatThrownBy(() -> {
            OrderItemRequest orderItemRequest = new OrderItemRequest(1L, 20, 18000);
            OrderCreateRequest orderCreateRequestForStockTest = new OrderCreateRequest(List.of(orderItemRequest), orderAddressCreate);
            orderService.save(orderCreateRequestForStockTest);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 주문수정테스트() {
        // 주문
        OrderItemRequest orderItemRequest1 = new OrderItemRequest(1L, 2, 18000);
        OrderItemRequest orderItemRequest2 = new OrderItemRequest(2L, 3, 27000);
        OrderAddressCreate orderAddressCreate = new OrderAddressCreate("56442", "서울특별시 용산구 한강로 11", "62-34", "홍길동", "010-1234-5678");

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of(orderItemRequest1, orderItemRequest2), orderAddressCreate);
        // 주문 완료
        orderService.save(orderCreateRequest);
        // 주문 완료

        OrderAddressUpdate orderAddressUpdate = new OrderAddressUpdate("23333", "부산광역시 영도구 태종로", "17-1", "홍길동", "010-5678-1234");
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest(1L, orderAddressUpdate);
        Long updatedOrderId = orderService.update(orderUpdateRequest);

        // 주문 수정 완료

        Order foundOrder = orderService.findById(updatedOrderId);
        assertThat(foundOrder.getDelivery().getAddress().getZipcode()).isEqualTo("23333");

    }

}