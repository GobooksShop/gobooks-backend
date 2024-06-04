package org.team.bookshop.domain.user.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.dto.UserDto;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.repository.RoleRepository;

@RequiredArgsConstructor
@Service
public class UserConvertService {

    private final RoleRepository roleRepository;

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
//        userDto.setRoles(user.getUserRoles().stream()
//            .map(userRole -> userRole.getRole().getRoleName().name())
//            .collect(Collectors.toSet()));
        return userDto;
    }
}
