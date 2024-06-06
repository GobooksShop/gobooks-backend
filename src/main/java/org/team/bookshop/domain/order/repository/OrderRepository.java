package org.team.bookshop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems join fetch o.delivery where o.id := orderId")
    Order findWithAllRelatedEntityById(@Param("orderId") Long orderId);
}
