package net.khaibq.javabackend.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDto {
    private Long id;
    private String password;
    @NotEmpty
    private String email;
    private String departmentCode;
    private Integer isDeleted;
    private List<String> roles = new ArrayList<>();
}
