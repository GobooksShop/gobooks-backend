package org.team.bookshop.domain.discount.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team.bookshop.domain.discount.dto.AddDiscountRequest;
import org.team.bookshop.domain.discount.dto.DiscountDto;
import org.team.bookshop.domain.discount.dto.UpdateDiscountRequest;
import org.team.bookshop.domain.discount.service.DiscountService;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountDto addDiscount(@Valid @RequestBody AddDiscountRequest request) {
        return discountService.addDiscount(request);
    }

    @PutMapping("/{id}")
    public DiscountDto updateDiscount(@PathVariable Long id, @Valid @RequestBody UpdateDiscountRequest request) {
        return discountService.updateDiscount(id, request);
    }

    @GetMapping("/{id}")
    public DiscountDto getDiscountById(@PathVariable Long id) {
        return discountService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
    }
}
