package net.khaibq.javabackend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequestDto {
    @NotBlank(message = "Key là bắt buộc")
    private String key;

    @NotNull(message = "Mật khẩu phải có độ dài từ 3-32 ký tự")
    @Length(min = 3, max = 32, message = "Mật khẩu phải có độ dài từ 3-32 ký tự")
    private String password;
}
