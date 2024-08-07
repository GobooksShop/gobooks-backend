package org.team.bookshop.domain.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.team.bookshop.global.util.BaseEntity;

@Entity
@Getter
@Builder
@Table(name = "users")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

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
