package net.khaibq.javabackend.dto.auth;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {
    @NotNull(message = "Tên đăng nhập là bắt buộc")
    private String username;
    
    @NotNull(message = "Mật khẩu là bắt buộc")
    private String password;
}
