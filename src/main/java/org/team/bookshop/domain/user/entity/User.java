
package org.team.bookshop.domain.user.entity;

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
import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.global.util.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@Entity
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User_Role> userRoles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Address> addresses;

    private UserRole role;
    private UserStatus status;
    private LocalDateTime deletedAt;
    private boolean termsAgreed;
    private boolean marketingAgreed;
    private boolean emailVerified;
    private LocalDateTime lastLogin;
}
