package org.team.bookshop.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.global.util.BaseEntity;

//세터 제거
@Setter
@Getter
@Table(name = "roles")
@Entity
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName roleName; // RoleName is an enum


    public enum RoleName {
        ADMIN, SELLER, USER, GUEST
    }
}
