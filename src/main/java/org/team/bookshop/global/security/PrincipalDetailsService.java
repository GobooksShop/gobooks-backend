package org.team.bookshop.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws EntityNotFoundException {
    Long id = Long.valueOf(userId);
    User user = userRepository.findById(id).orElseThrow(() -> {
      return new EntityNotFoundException("해당 유저를 찾을 수 없습니다.");
    });

    return new PrincipalDetails(user);
  }
}
