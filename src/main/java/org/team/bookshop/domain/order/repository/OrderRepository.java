package org.team.bookshop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
