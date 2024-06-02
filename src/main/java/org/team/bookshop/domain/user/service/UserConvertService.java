package org.team.bookshop.domain.user.service;

import org.springframework.stereotype.Service;
import org.team.bookshop.domain.user.dto.UserDto;
import org.team.bookshop.domain.user.entity.User;

@Service
public class UserConvertService {

    //매퍼 사용 고려.. public은 좀 위험할듯)
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());
        return new UserDto(user.getEmail(), user.getName(), user.getRoles());
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setRoles(userDto.getRoles());
        return user;
    }
}
