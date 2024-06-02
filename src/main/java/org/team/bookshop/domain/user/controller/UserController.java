package org.team.bookshop.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.user.dto.UserDto;
import org.team.bookshop.domain.user.dto.UserJoinDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.service.UserConvertService;
import org.team.bookshop.domain.user.service.UserService;

@RestController
@Slf4j
@Tag(name = "유저")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserConvertService userConvertService;


    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserJoinDto userJoinDto,
        BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        User savedUser = userService.saveUser(userJoinDto);
        UserDto savedUserDto = userConvertService.convertToDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userConvertService.convertToDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userConvertService.convertToEntity(userDto));
        return ResponseEntity.ok(userConvertService.convertToDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
            .map(userConvertService::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
}
