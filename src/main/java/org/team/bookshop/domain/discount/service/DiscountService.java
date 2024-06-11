package org.team.bookshop.domain.discount.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.discount.dto.AddDiscountRequest;
import org.team.bookshop.domain.discount.dto.DiscountDto;
import org.team.bookshop.domain.discount.dto.UpdateDiscountRequest;
import org.team.bookshop.domain.discount.entity.Discount;
import org.team.bookshop.domain.discount.repository.DiscountRepository;
import org.team.bookshop.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    @Transactional
    public DiscountDto addDiscount(AddDiscountRequest request) {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.getProductId()));

        Discount discount = new Discount();
        discount.setDescription(request.getDescription());
        discount.setPercentage(request.getPercentage());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setProduct(product);

        discountRepository.save(discount);
        return new DiscountDto(discount);
    }

    @Transactional
    public DiscountDto updateDiscount(Long id, UpdateDiscountRequest request) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + id));

        discount.setDescription(request.getDescription());
        discount.setPercentage(request.getPercentage());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());

        discountRepository.save(discount);
        return new DiscountDto(discount);
    }

    public DiscountDto findById(Long id) {
        return discountRepository.findById(id)
                .map(DiscountDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + id));
    }

    public void deleteDiscount(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + id));
        discountRepository.delete(discount);
    }
}
