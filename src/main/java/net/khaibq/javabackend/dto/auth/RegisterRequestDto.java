package net.khaibq.javabackend.dto.auth;

import jakarta.validation.constraints.Email;
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
public class RegisterRequestDto {
    @NotNull(message = "Tên đăng nhập phải có độ dài từ 3-32 ký tự")
    @Length(min = 3, max = 32, message = "Tên đăng nhập phải có độ dài từ 3-32 ký tự")
    private String username;

    @NotNull(message = "Mật khẩu phải có độ dài từ 3-32 ký tự")
    @Length(min = 3, max = 32, message = "Mật khẩu phải có độ dài từ 3-32 ký tự")
    private String password;

    @NotNull(message = "Email là bắt buộc")
    @Email(message = "Định dạng email không hợp lệ")
    private String email;

    private Integer gender;
}
