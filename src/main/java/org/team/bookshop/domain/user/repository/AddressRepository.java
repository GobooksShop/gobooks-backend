package org.team.bookshop.domain.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.order.entity.Delivery;
import org.team.bookshop.domain.user.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    void deleteByUserId(Long userId);

    List<Address> findByUserId(Long userId);

    Address findByDelivery(Delivery delivery);
}
