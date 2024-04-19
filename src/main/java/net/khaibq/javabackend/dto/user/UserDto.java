package net.khaibq.javabackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Integer gender;
    private String address;
    private String phoneNumber;
    private Integer level;
    private String avatar;
    private Integer isDeleted;
}
