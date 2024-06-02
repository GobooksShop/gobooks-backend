package org.team.bookshop.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.user.entity.User_Role;

public interface UserRoleRepository extends JpaRepository<User_Role, Long> {

    Optional<User_Role> findByUserId(Long userId);
}
