package org.team.bookshop.domain.user.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRoleDto {

    private Set<String> roleName;
}
