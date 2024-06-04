package org.team.bookshop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
