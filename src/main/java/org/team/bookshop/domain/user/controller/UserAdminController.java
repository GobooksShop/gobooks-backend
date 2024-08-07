package org.team.bookshop.domain.user.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.user.dto.UserPostDto;
import org.team.bookshop.domain.user.dto.UserStatusResponseDto;
import org.team.bookshop.domain.user.service.UserService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserAdminController {

  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<UserPostDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/users/status")
  public ResponseEntity<UserStatusResponseDto> getUserStatus() {
    return ResponseEntity.ok(userService.getUserStatus());
  }
}