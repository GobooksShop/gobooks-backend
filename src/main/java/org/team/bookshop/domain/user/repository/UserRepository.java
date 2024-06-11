package org.team.bookshop.domain.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);

    List<User> findAll();
    
    Optional<User> findByProviderId(@Param("providerId") String providerId);

    boolean existsByEmail(@Param("email") String email);
}
