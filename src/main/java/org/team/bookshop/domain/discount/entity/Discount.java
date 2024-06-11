package org.team.bookshop.domain.discount.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.team.bookshop.domain.product.entity.Product;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private double percentage;

    @ManyToOne
    private Product product;

    private LocalDate startDate;
    private LocalDate endDate;
}
