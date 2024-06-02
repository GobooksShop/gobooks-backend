package org.team.bookshop.domain.user.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.user.dto.UserJoinDto;
import org.team.bookshop.domain.user.entity.Role;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email " + email));
    }


    @Transactional(readOnly = false)
    public User saveUser(UserJoinDto userJoinDto) throws Exception {
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent()) {
            throw new Exception("Email already in use.");
        }

        User user = new User();
        user.setEmail(userJoinDto.getEmail());
        user.setName(userJoinDto.getName());
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));

        Set<Role> roles = userJoinDto.getRoles() != null ? userJoinDto.getRoles() : new HashSet<>();
        if (roles.isEmpty()) {
            roles.add(Role.USER); // Assign default role if none provided
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

}
