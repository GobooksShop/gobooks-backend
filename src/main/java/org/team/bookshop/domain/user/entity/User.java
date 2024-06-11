package org.team.bookshop.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.team.bookshop.global.util.BaseEntity;

@Getter
@Setter
@Table(name = "users")
@Entity
@DynamicUpdate
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String phone;

    @OneToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @OneToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    private String providerId; //OAuth2 기존 로그인 정보를 저장하기 위한 providerId
    private UserRole role;
    private UserStatus status;
    private LocalDateTime deletedAt;
    private boolean termsAgreed;
    private boolean marketingAgreed;
    private boolean emailVerified;
    private LocalDateTime lastLogin;
}
