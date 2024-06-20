package org.team.bookshop.domain.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.order.dto.OrderAbstractResponse;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.user.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("select o from Order o " +
      "join fetch o.orderItems oi " +
      "join fetch o.delivery d " +
      "join fetch oi.product " +
      "where o.id = :orderId")
  Order findWithAllRelatedEntityById(@Param("orderId") Long orderId);

  @Query("select o from Order o " +
      "join fetch o.orderItems oi " +
      "join fetch oi.product " +
      "where o.id = :orderId")
  Order findWithOrderItems(@Param("orderId") long orderId);

  Optional<Order> findByMerchantUid(String merchantUid);


  void deleteByDeliveryId(Long deliveryId);

  @Query("select o from Order o "
      + "join fetch o.orderItems oi "
      + "join fetch oi.product p "
      + "where user = :user")
  List<Order> findOrdersByUser(User user);

  @Query("select o from Order o join fetch o.delivery d where o.merchantUid = :merchantUid")
  Optional<Order> findByMerchantUidWithDelivery(@Param("merchantUid") String merchantUid);
}
