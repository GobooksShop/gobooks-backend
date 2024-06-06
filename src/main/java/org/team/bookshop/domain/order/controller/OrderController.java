package org.team.bookshop.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.dto.OrderCreateRequest;
import org.team.bookshop.domain.order.dto.OrderDeleteRequest;
import org.team.bookshop.domain.order.dto.OrderUpdateRequest;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        orderService.save(orderCreateRequest);
    }

    @PatchMapping("/update")
    public void updateOrder(OrderUpdateRequest orderUpdateRequest) {
        orderService.update(orderUpdateRequest);
    }

    @DeleteMapping("/delete")
    public void deleteOrder(OrderDeleteRequest orderDeleteRequest) {
        orderService.delete(orderDeleteRequest);
    }
}
