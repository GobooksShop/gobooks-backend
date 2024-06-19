package org.team.bookshop.domain.product.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.product.entity.ProductImgDetail;
import org.team.bookshop.domain.product.repository.ProductImgDetailRepository;

@Transactional(readOnly = false)
@RequiredArgsConstructor
@Service
public class ProductImgDetailService {

    private final ProductImgDetailRepository productImgDetailRepository;

    public ProductImgDetail save(ProductImgDetail productImgDetail) {
        return productImgDetailRepository.save(productImgDetail);
    }

    @Transactional(readOnly = true)
    public Optional<ProductImgDetail> findByProductId(Long productId) {
        return productImgDetailRepository.findByProductId(productId);
    }

    public void deleteById(Long id) {
        productImgDetailRepository.deleteById(id);
    }
}
