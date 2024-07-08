package org.team.bookshop.global.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.team.bookshop.domain.user.entity.UserRole;

@Aspect
@Component
public class AdminAspect {

  @Before("execution(* org.team.bookshop..*Admin*Controller.*(..))")
  public void checkAdminRole(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication == null || authentication.getAuthorities().stream()
        .noneMatch(auth -> auth.getAuthority().equals(UserRole.ADMIN.getRoleName()))){
        throw new AccessDeniedException("Admin role required");
    }
  }
}
