package org.team.bookshop.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.user.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
  Optional<Token> findByToken(String token);
}
