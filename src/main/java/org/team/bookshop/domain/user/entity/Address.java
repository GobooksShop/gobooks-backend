package org.team.bookshop.domain.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String recipientName;

    @Column
    private String additionalInfo;

    @Column
    private String zipCode;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;

}
