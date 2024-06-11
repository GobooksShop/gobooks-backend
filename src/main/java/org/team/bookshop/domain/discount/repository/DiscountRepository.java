package org.team.bookshop.domain.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.discount.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
