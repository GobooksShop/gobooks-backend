package org.team.bookshop.domain.order.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.user.entity.Address;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
