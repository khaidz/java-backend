package net.khaibq.javabackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordRequestDto {
    @NotNull(message = "Email là bắt buộc")
    @Email(message = "Định dạng email không hợp lệ")
    private String email;
}
