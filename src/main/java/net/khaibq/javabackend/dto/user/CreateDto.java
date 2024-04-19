package net.khaibq.javabackend.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDto {
    @NotNull
    @Length(min = 6, max = 50)
    private String username;
    @NotNull
    @Length(min = 6, max = 50)
    private String password;
    @NotEmpty
    private String email;
}
