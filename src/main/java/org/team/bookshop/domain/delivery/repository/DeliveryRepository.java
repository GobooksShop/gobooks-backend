package org.team.bookshop.domain.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.delivery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
