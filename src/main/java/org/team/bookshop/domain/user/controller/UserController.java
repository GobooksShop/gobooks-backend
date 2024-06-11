package org.team.bookshop.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.user.dto.UserJoinDto;
import org.team.bookshop.domain.user.dto.UserPostDto;
import org.team.bookshop.domain.user.service.UserService;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@RestController
@Slf4j
@Tag(name = "유저")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserJoinDto userJoinDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
        }
        userService.saveUser(userJoinDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPostDto> getUserById(@PathVariable Long id) {
        UserPostDto userPostDto = userService.getUserById(id);
        return ResponseEntity.ok(userPostDto);
    }

    //수정후 다시 정보 받아서 수정화면 그대로 띄우는 걸로 생각했는데 어떻게 생각하시는지!
    @PutMapping("/{id}")
    public ResponseEntity<UserPostDto> updateUser(@PathVariable Long id,
        @RequestBody UserPostDto UserPostDto) {

        return ResponseEntity.ok(userService.updateUser(id, UserPostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserPostDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
