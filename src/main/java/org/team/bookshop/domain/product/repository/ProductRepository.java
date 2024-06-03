package org.team.bookshop.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
