package org.team.bookshop.domain.discount.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDiscountRequest {

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Percentage is required")
    private double percentage;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Product ID is required")
    private Long productId;
}
