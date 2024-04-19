package net.khaibq.javabackend.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDto {
    private Long id;
    private String password;
    @NotEmpty
    private String email;
    private Integer isDeleted;
}
