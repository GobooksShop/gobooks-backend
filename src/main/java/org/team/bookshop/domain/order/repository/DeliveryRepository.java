package org.team.bookshop.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByAddressId(Long addressId);

    void deleteByAddressId(Long id);
}
