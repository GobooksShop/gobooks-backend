package org.team.bookshop.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.service.UserService;

@RestController
@Slf4j
@Tag(name = "유저")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto loginDto,
        BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByEmail(loginDto.getEmail());
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = jwtTokennizer.createToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokennizer.createRefreshToken(user.getEmail(),
            user.getRole().name());

    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserJoinDto userJoinDto,
        BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        User savedUser = userService.saveUser(userJoinDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Request to get user by id: {}", id);
        return Optional.ofNullable(userService.getUserById(id))
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        log.info("Request to update user with id: {}", id);
        try {
            User updatedUser = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Request to delete user with id: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Request to get all users");
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
