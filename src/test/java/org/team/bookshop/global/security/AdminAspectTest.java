package org.team.bookshop.global.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@Aspect
public class AdminAspectTest {

  private AdminAspect adminAspect = new AdminAspect();

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void shouldAllowAccessForAdmin() throws Throwable {
    adminAspect.checkAdminRole();
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  void shouldDenyAccessForNonAdmin() throws Throwable {
    assertThatThrownBy(() -> adminAspect.checkAdminRole())
        .isInstanceOf(AccessDeniedException.class)
        .hasMessage("Admin role required");
  }
}

