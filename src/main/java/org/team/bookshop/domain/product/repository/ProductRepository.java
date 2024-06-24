package org.team.bookshop.domain.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @Query("select p from Product p where p.id in :productIds order by p.id")
    List<Product> findByProductIds(@Param("productIds") List<Long> productIds);

    @Query(value = "select * from products order by rand() limit :count", nativeQuery = true)
    List<Product> findRandomProducts(@Param("count") int count);
}
