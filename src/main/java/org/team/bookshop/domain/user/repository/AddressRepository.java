package org.team.bookshop.domain.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a where a.user.id = :userid and a.id = :addressId ")
    Address findAddressByUserId(Long userid, Long addressId);

    void deleteByUserId(Long userId);

    List<Address> findByUserId(Long userId);

    Optional<Address> findByUserAndLabel(User user, String label);
}
