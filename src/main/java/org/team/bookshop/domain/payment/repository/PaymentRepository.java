package org.team.bookshop.domain.payment.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.Order;
import org.team.bookshop.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  Optional<Payment> findPaymentByOrder(Order order);
}
