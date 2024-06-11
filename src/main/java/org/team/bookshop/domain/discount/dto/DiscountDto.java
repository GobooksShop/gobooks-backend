package org.team.bookshop.domain.discount.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.domain.discount.entity.Discount;

@Getter
@Setter
public class DiscountDto {

    private Long id;
    private String description;
    private double percentage;
    private LocalDate startDate;
    private LocalDate endDate;

    public DiscountDto(Discount discount) {
        this.id = discount.getId();
        this.description = discount.getDescription();
        this.percentage = discount.getPercentage();
        this.startDate = discount.getStartDate();
        this.endDate = discount.getEndDate();
    }
}
