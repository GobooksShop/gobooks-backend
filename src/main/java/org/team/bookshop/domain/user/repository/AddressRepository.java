package org.team.bookshop.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.user.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByDelivery(Delivery delivery);
}
