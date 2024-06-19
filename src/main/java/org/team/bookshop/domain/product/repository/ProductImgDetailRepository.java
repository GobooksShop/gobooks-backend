package org.team.bookshop.domain.product.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.product.entity.ProductImgDetail;

public interface ProductImgDetailRepository extends JpaRepository<ProductImgDetail, Long> {

    Optional<ProductImgDetail> findByProductId(Long productId);

}
