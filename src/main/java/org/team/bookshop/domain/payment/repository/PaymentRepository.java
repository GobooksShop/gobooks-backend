package org.team.bookshop.domain.payment.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.payment.entity.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

  Optional<Payments> findPaymentsByOrder(Order order);
}
