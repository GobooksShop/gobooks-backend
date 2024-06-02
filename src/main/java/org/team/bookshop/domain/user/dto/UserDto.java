package org.team.bookshop.domain.user.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.user.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String email;
    private String name;
    private Set<Role> roles;

}
