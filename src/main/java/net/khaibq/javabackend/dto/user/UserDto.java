package net.khaibq.javabackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.khaibq.javabackend.dto.role.RoleDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String deptCode;
    private List<RoleDto> roles;
    private Integer isDeleted;
}
