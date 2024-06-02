package org.team.bookshop.domain.user.service;


import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.user.dto.UserDto;
import org.team.bookshop.domain.user.dto.UserJoinDto;
import org.team.bookshop.domain.user.dto.UserRoleDto;
import org.team.bookshop.domain.user.dto.UserUpdateDto;
import org.team.bookshop.domain.user.entity.Role;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.entity.User_Role;
import org.team.bookshop.domain.user.repository.RoleRepository;
import org.team.bookshop.domain.user.repository.UserRepository;
import org.team.bookshop.domain.user.repository.UserRoleRepository;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserConvertService userConvertService;

    @PostConstruct
    public void init() {
        if (roleRepository.findByRoleName(Role.RoleName.USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setRoleName(Role.RoleName.USER);
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRoleName(Role.RoleName.ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName(Role.RoleName.ADMIN);
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByRoleName(Role.RoleName.SELLER).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName(Role.RoleName.SELLER);
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByRoleName(Role.RoleName.GUEST).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName(Role.RoleName.GUEST);
            roleRepository.save(adminRole);
        }
    }

    @Transactional(readOnly = false)
    public void saveUser(UserJoinDto userJoinDto) throws Exception {
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent()) {
            throw new Exception("Email already in use.");
        }

        User user = new User();
        user.setEmail(userJoinDto.getEmail());
        user.setName(userJoinDto.getName());
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));

        Set<User_Role> userRoles = createRoles(user, userJoinDto.getRoles());
        user.setUserRoles(userRoles);

        userRepository.save(user);
    }


    public UserDto getUserById(Long id) {
        User user = userRepository.findByIdWithRoles(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return userConvertService.convertToDto(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAllWithRoles();
    }


    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    @Transactional(readOnly = false)
    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());

        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        userRepository.save(user);

        Set<String> roleNames = user.getUserRoles().stream()
            .map(userRole -> userRole.getRole().getRoleName().name())
            .collect(Collectors.toSet());

        return new UserDto(user.getEmail(), user.getName(), roleNames);
    }

    @Transactional(readOnly = false)
    public void updateUserRole(Long userId, UserRoleDto userRoleDto) {
        Set<String> roleNames = userRoleDto.getRoleName();

        User_Role userRole = userRoleRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException(
                "User role not found for userId: " + userId));

        Set<Role> roles = roleNames.stream()
            .map(roleName -> roleRepository.findByRoleName(Role.RoleName.valueOf(roleName))
                .orElseThrow(() -> new EntityNotFoundException(
                    "Role not found with roleName: " + roleName)))
            .collect(Collectors.toSet());

        userRole.setRoles(roles);

        userRoleRepository.save(userRole);
    }

    @Transactional(readOnly = false)
    public void changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    protected Set<User_Role> createRoles(User user, Set<String> roleNames)
        throws EntityNotFoundException {
        Set<User_Role> userRoles = new HashSet<>();
        for (String roleNameStr : roleNames) {
            Role.RoleName roleName = Role.RoleName.valueOf(roleNameStr);
            Role existingRole = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleNameStr));

            User_Role userRole = new User_Role();
            userRole.setUser(user);
            userRole.setRole(existingRole);
            userRoles.add(userRole);
        }
        return userRoles;
    }
}
